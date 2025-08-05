package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bank.model.Customer;
import com.bank.model.KYCDocument;
import com.bank.proxy.CustomerProxy;
import com.bank.services.KYCDocumentService;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/kyc/api")
@CrossOrigin(origins = "http://localhost:7071")
public class KYCController {

	@Autowired
	private KYCDocumentService kycDocumentService;
	
	@Autowired
	private CustomerProxy customerProxy;

	@GetMapping("/upload-form")
	public String showUploadForm(@RequestParam(value = "customerId", required = false) String customerId, Model model) {
		// Now, customerId will be non-null if coming directly from registration
//		model.addAttribute("customerId", customerId); // put it into model for the form
		return "KYC upload form Loaded";
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadKycDocuments(
	        @RequestParam("customerId") String customerId,
	        @RequestParam("aadharNumber") String aadharNumber,
	        @RequestParam("panNumber") String panNumber,
	        @RequestParam("aadharFront") MultipartFile aadharFront,
	        @RequestParam("aadharBack") MultipartFile aadharBack,
	        @RequestParam("panFront") MultipartFile panFront,
	        @RequestParam("panBack") MultipartFile panBack,
	        @RequestParam("photograph") MultipartFile photograph) {

	    try {
	        if (aadharNumber == null || aadharNumber.trim().isEmpty() || panNumber == null || panNumber.trim().isEmpty()) {
	            return ResponseEntity.badRequest().body(Map.of("error", "Aadhar number and PAN number are required"));
	        }

	        if (!aadharNumber.matches("\\d{12}")) {
	            return ResponseEntity.badRequest().body(Map.of("error", "Aadhar number must be exactly 12 digits"));
	        }

	        if (!panNumber.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
	            return ResponseEntity.badRequest().body(Map.of("error", "PAN number format is invalid"));
	        }

	        KYCDocument savedDocument = kycDocumentService.saveKycDocument(customerId, aadharNumber.trim(), panNumber.trim(),
	                aadharFront, aadharBack, panFront, panBack, photograph);

	        return ResponseEntity.ok(savedDocument);

	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(Map.of("error", "File validation error: " + e.getMessage()));
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(Map.of("error", e.getMessage()));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(Map.of("error", "An unexpected error occurred. Please try again."));
	    }
	}


	@GetMapping("/view-application")
	public String viewApplication(@RequestParam(value = "documentId", required = true) Long id, Model model) {
		Optional<KYCDocument> documentOpt = kycDocumentService.getDocumentById(id);
		if (documentOpt.isPresent()) {
			KYCDocument document = documentOpt.get();

			model.addAttribute("document", document);

			try {
				String customerId = document.getCustomerId();
				Customer customer = customerProxy.getCustomer(customerId);
				model.addAttribute("customer", customer);
			} catch (Exception e) {
				// Log error or handle customer fetch failure gracefully
				model.addAttribute("customer", null);
			}

			return "customerwithkyc";
		} else {
			return "redirect:/kyc/api/upload?error=Document not found";
		}

	}
	
	

	@GetMapping("/all-documents")
	public ResponseEntity<List<KYCDocument>> getAllDocuments() {
	    List<KYCDocument> docs = kycDocumentService.getAllDocuments();
	    return ResponseEntity.ok(docs); // returns 200 OK, [] if empty, never null
	}

	
	@GetMapping("/documents/{id}")
    public ResponseEntity<KYCDocument> getDocumentById(@PathVariable("id") Long id) {
        Optional<KYCDocument> documentOpt = kycDocumentService.getDocumentById(id);
        return documentOpt.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }
	   

	 @PutMapping("/change-status")
	    public ResponseEntity<?> updateDocumentStatus(
	    		@RequestParam("id") Long documentId,
	            @RequestParam("status") String status) {
	        try {
	            Optional<KYCDocument> documentOpt = kycDocumentService.getDocumentById(documentId);
	            if (documentOpt.isPresent()) {
	                kycDocumentService.updateDocumentStatus(documentId, status);
	                return ResponseEntity.ok(Map.of("success", "Document status updated successfully"));
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(Map.of("error", "Document not found"));
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("error", "Error updating document status: " + e.getMessage()));
	        }
	    }
	


//***************************************************************************************************

//    @GetMapping("/health")
//    @ResponseBody
//    public String healthCheck() {
//        return "KYC Service is UP";
//    }

	// API endpoint for file upload (called by frontend)
//    @PostMapping("/upload")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> uploadKYCDocuments(
//            @RequestParam("fullName") String fullName,
//            @RequestParam("email") String email,
//            @RequestParam("phoneNumber") String phoneNumber,
//            @RequestParam(value = "aadharFront", required = false) MultipartFile aadharFront,
//            @RequestParam(value = "aadharBack", required = false) MultipartFile aadharBack,
//            @RequestParam(value = "panFront", required = false) MultipartFile panFront,
//            @RequestParam(value = "panBack", required = false) MultipartFile panBack,
//            @RequestParam(value = "photograph", required = false) MultipartFile photograph) {
//        
//        Map<String, Object> response = new HashMap<>();
//        
//        try {
//            // Validate required fields
//            if (fullName == null || fullName.trim().isEmpty() ||
//                email == null || email.trim().isEmpty() ||
//                phoneNumber == null || phoneNumber.trim().isEmpty()) {
//                response.put("error", "All personal details are required");
//                return ResponseEntity.badRequest().body(response);
//            }
//            
//            // Validate email format
//            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
//                response.put("error", "Please enter a valid email address");
//                return ResponseEntity.badRequest().body(response);
//            }
//            
//            // Validate phone number format
//            if (!phoneNumber.matches("^[0-9]{10}$")) {
//                response.put("error", "Please enter a valid 10-digit phone number");
//                return ResponseEntity.badRequest().body(response);
//            }
//            
//            KYCDocument savedDocument = kycDocumentService.saveKYCDocument(
//                fullName.trim(), email.trim(), phoneNumber.trim(),
//                aadharFront, aadharBack, panFront, panBack, photograph
//            );
//            
//            response.put("success", true);
//            response.put("message", "KYC documents uploaded successfully!");
//            response.put("id", savedDocument.getId());
//            
//            return ResponseEntity.ok(response);
//            
//        } catch (IOException e) {
//            response.put("error", "File validation error: " + e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        } catch (RuntimeException e) {
//            response.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        } catch (Exception e) {
//            response.put("error", "An unexpected error occurred. Please try again.");
//            return ResponseEntity.internalServerError().body(response);
//        }
//    }

	// Direct upload endpoint (for direct backend access)
//    @PostMapping("/direct-upload")
//    public String directUploadKYCDocuments(
//            @RequestParam("fullName") String fullName,
//            @RequestParam("email") String email,
//            @RequestParam("phoneNumber") String phoneNumber,
//            @RequestParam("aadharFront") MultipartFile aadharFront,
//            @RequestParam("aadharBack") MultipartFile aadharBack,
//            @RequestParam("panFront") MultipartFile panFront,
//            @RequestParam("panBack") MultipartFile panBack,
//            @RequestParam("photograph") MultipartFile photograph,
//            RedirectAttributes redirectAttributes) {
//        
//        try {
//            KYCDocument savedDocument = kycDocumentService.saveKYCDocument(
//                fullName.trim(), email.trim(), phoneNumber.trim(),
//                aadharFront, aadharBack, panFront, panBack, photograph
//            );
//            
//            redirectAttributes.addFlashAttribute("success",
//                "KYC documents uploaded successfully! Reference ID: " + savedDocument.getId());
//                
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Upload failed: " + e.getMessage());
//        }
//        
//        return "redirect:/kyc/api/upload-form";
//    }

//    @GetMapping("/upload-form")
//    public String showUploadForm(Model model) {
//        return "kyc-upload";
//    }

}
