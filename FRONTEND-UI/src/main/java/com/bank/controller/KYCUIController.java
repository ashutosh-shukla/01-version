package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bank.model.Customer;
import com.bank.model.KYCDocument;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/kyc")
public class KYCUIController {

    // Assuming your API Gateway is running on localhost:8080
    // and the KYC Service is exposed at /kyc/api
    private final String API_GATEWAY_URL = "http://localhost:8080";

    @Autowired
    private RestTemplate restTemplate;

    
//    @GetMapping("/form/{customerId}")
//    public String showUploadForm() {
//        return "kyc-upload";
//    }
    
    @GetMapping("/form")
    public String showUploadForm(@RequestParam(value = "customerId", required = false) String customerId, Model model) {
        // Now, customerId will be non-null if coming directly from registration
        model.addAttribute("customerId", customerId); // put it into model for the form
        return "kyc-upload";
    }
    
    @GetMapping("/form/success")
    public String showUploadSuccess() {
		return "KYCSuccessfull";
	}
    
    @PostMapping("/upload")
    public String uploadKYCDocuments(@RequestParam("customerId") String customerId,
			@RequestParam("aadharNumber") String aadharNumber, @RequestParam("panNumber") String panNumber,
			@RequestParam("aadharFront") MultipartFile aadharFront,
			@RequestParam("aadharBack") MultipartFile aadharBack, @RequestParam("panFront") MultipartFile panFront,
			@RequestParam("panBack") MultipartFile panBack, @RequestParam("photograph") MultipartFile photograph,
			Model model, RedirectAttributes redirectAttributes) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("customerId", customerId.trim());
            body.add("aadharNumber", aadharNumber.trim());
            body.add("panNumber", panNumber.trim());
            

            // Helper function to add MultipartFile to MultiValueMap
            addFileToBody(body, "aadharFront", aadharFront);
            addFileToBody(body, "aadharBack", aadharBack);
            addFileToBody(body, "panFront", panFront);
            addFileToBody(body, "panBack", panBack);
            addFileToBody(body, "photograph", photograph);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Call the KYC microservice through the API Gateway
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    API_GATEWAY_URL + "/kyc/api/upload", // This path maps to your KYCService's @RequestMapping("/kyc/api") and @PostMapping("/upload")
                    requestEntity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                model.addAttribute("success",
                        "KYC documents uploaded successfully! Reference ID: " + responseBody.get("id"));
                model.addAttribute("customerId", responseBody.get("customerId"));
            } else {
                model.addAttribute("error", "Upload failed with status: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException e) {
            // Handle specific HTTP client errors (e.g., 400 Bad Request from backend validation)
            String errorMessage = "Upload failed: " + e.getMessage();
            try {
                // Attempt to parse the error message from the backend response body
                Map<String, Object> errorResponse = e.getResponseBodyAs(Map.class);
                if (errorResponse != null && errorResponse.containsKey("error")) {
                    errorMessage = (String) errorResponse.get("error");
                }
            } catch (Exception parseException) {
                // Log parsing error, but use the default message
                System.err.println("Failed to parse error response: " + parseException.getMessage());
            }
            redirectAttributes.addFlashAttribute("error", errorMessage);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error",
                    "File processing error: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            redirectAttributes.addFlashAttribute("error",
                    "An unexpected error occurred during upload: " + e.getMessage());
        }

        return "KYCSuccessfull"; // Redirect back to the upload form
    }

    
    private void addFileToBody(MultiValueMap<String, Object> body, String paramName, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            body.add(paramName, new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });
        }
    }

    

    
    @GetMapping("/view-application")
    public String viewApplication(@RequestParam("documentId") Long id, Model model) {
        try {
            // Call KYC microservice to get the KYC document
            ResponseEntity<KYCDocument> docResponse = restTemplate.getForEntity(
                    API_GATEWAY_URL + "/kyc/api/documents/" + id,
                    KYCDocument.class);

            if (docResponse.getStatusCode() == HttpStatus.OK && docResponse.getBody() != null) {
                KYCDocument document = docResponse.getBody();
                model.addAttribute("document", document);

                // Fetch customer details via customerProxy or directly via REST call
                String customerId = document.getCustomerId();

                try {
                    ResponseEntity<Customer> customerResponse = restTemplate.getForEntity(
                            API_GATEWAY_URL + "/customers/" + customerId,
                            Customer.class);

                    if (customerResponse.getStatusCode() == HttpStatus.OK && customerResponse.getBody() != null) {
                        model.addAttribute("customer", customerResponse.getBody());
                    } else {
                        model.addAttribute("customer", null);
                    }
                } catch (Exception e) {
                    // Log or handle failure gracefully
                    model.addAttribute("customer", null);
                }

                return "customerwithkyc"; // The view name to render
            } else {
                // Document not found, redirect to upload page or error page
                return "redirect:/kyc/form?error=Document not found";
            }
        } catch (Exception e) {
            // Handle REST call failures or other exceptions
            model.addAttribute("error", "Failed to fetch KYC document: " + e.getMessage());
            return "error"; // Or any error view you have
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    @GetMapping("/admin")
    public String showAdmin(Model model) {
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(
                    API_GATEWAY_URL + "/kyc/api/admin", List.class); // This path maps to your KYCService's /kyc/api/admin
            model.addAttribute("documents", response.getBody());
        } catch (HttpClientErrorException e) {
             model.addAttribute("error", "Error fetching documents: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            model.addAttribute("error", "Unable to fetch KYC documents: " + e.getMessage());
        }
        return "admin-panel-simple";
    }

    
    @GetMapping("/admin/view/{id}")
    public String viewDocument(@PathVariable Long id, Model model) {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    API_GATEWAY_URL + "/kyc/api/admin/view/" + id, Map.class); // This path maps to your KYCService's /kyc/api/admin/view/{id}
            model.addAttribute("document", response.getBody());
            return "document-view";
        } catch (HttpClientErrorException e) {
            // Document not found or other client errors
            return "redirect:/kyc/admin?error=Document not found: " + e.getMessage();
        } catch (Exception e) {
            return "redirect:/kyc/admin?error=Error viewing document: " + e.getMessage();
        }
    }

    
    @PostMapping("/admin/delete/{id}")
    public String deleteDocument(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Using exchange for DELETE as postForObject might not be suitable for DELETE with no request body
            restTemplate.exchange(API_GATEWAY_URL + "/kyc/api/admin/delete/" + id, HttpMethod.DELETE, null, String.class);
            redirectAttributes.addFlashAttribute("success", "Document deleted successfully");
        } catch (HttpClientErrorException e) {
             redirectAttributes.addFlashAttribute("error", "Error deleting document: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting document: " + e.getMessage());
        }
        return "redirect:/kyc/admin";
    }

    
    @PostMapping("/admin/update-status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam("status") String status,
                               RedirectAttributes redirectAttributes) {
        try {
            Map<String, String> request = new HashMap<>();
            request.put("status", status);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

            restTemplate.postForObject(API_GATEWAY_URL + "/kyc/api/admin/update-status/" + id, entity, String.class);
            redirectAttributes.addFlashAttribute("success", "Status updated successfully");
        } catch (HttpClientErrorException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update status: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update status: " + e.getMessage());
        }
        return "redirect:/kyc/admin";
    }

    
    
}