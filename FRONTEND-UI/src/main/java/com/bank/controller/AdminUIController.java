//package com.bank.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.bank.model.Customer;
//import com.bank.model.KYCDocument;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminUIController {
//    
//    private final String API_GATEWAY_URL = "http://localhost:8080";
//    
//    @Autowired
//    private RestTemplate restTemplate;
//    
//    @GetMapping
//    public String showAdminPanel(Model model) {
//
//        //  Fetch all KYC documents (use type-safe response)
//        ResponseEntity<List<KYCDocument>> response = restTemplate.exchange(
//                API_GATEWAY_URL + "/kyc/api/all-documents",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<KYCDocument>>() {}
//        );
//        List<KYCDocument> documents = response.getBody();
//        if (documents == null) {
//            documents = Collections.emptyList();
//        }
//
//        //  Extract unique customerIds
//        Set<String> customerIds = documents.stream()
//                                           .map(KYCDocument::getCustomerId)
//                                           .filter(Objects::nonNull)
//                                           .collect(Collectors.toSet());
//
//        //  Fetch customer details for all unique customerIds
//        Map<String, Customer> customerMap = new HashMap<>();
//        for (String customerId : customerIds) {
//            if (customerId == null || customerId.isBlank()) continue;
//            try {
//                ResponseEntity<Customer> customerResponse = restTemplate.getForEntity(
//                        API_GATEWAY_URL + "/customers/" + customerId,
//                        Customer.class
//                );
//                Customer customer = customerResponse.getBody();
//                if (customer != null) {
//                    customerMap.put(customerId, customer);
//                }
//            } catch (Exception e) {
//                // Optionally log warning
//            }
//        }
//
//        
//        model.addAttribute("documents", documents);
//        model.addAttribute("customerMap", customerMap);
//
//        return "admin-panel-simple";
//    }
//    
//    
//    @GetMapping("/view/{id}")
//    public String viewDocument(@PathVariable Long id, Model model) {
//        // KYC Document lookup
//        ResponseEntity<KYCDocument> docResponse = restTemplate.getForEntity(
//                API_GATEWAY_URL + "/kyc/api/documents/" + id,
//                KYCDocument.class
//        );
//
//        if (docResponse.getStatusCode() == HttpStatus.OK && docResponse.getBody() != null) {
//            KYCDocument document = docResponse.getBody();
//            model.addAttribute("document", document);
//
//            // Customer lookup (optional, may fail)
//            try {
//                String customerId = document.getCustomerId();
//                ResponseEntity<Customer> customerResponse = restTemplate.getForEntity(
//                        API_GATEWAY_URL + "/customers/" + customerId,
//                        Customer.class
//                );
//                if (customerResponse.getStatusCode() == HttpStatus.OK) {
//                    model.addAttribute("customer", customerResponse.getBody());
//                } else {
//                    model.addAttribute("customer", null);
//                }
//            } catch (Exception e) {
//                model.addAttribute("customer", null); // Fallback
//            }
//
//            return "document-view";
//        } else {
//            // Couldn’t find document: redirect to admin documents page with error msg
//            model.addAttribute("error", "Document not found");
//            return "redirect:/admin?error=Document+not+found";
//        }
//    }
//    
//    
//    
//    
//    
//    @PostMapping("/admin/update-status")
//    public String updateStatus(@RequestParam(value = "customerId", required = true) String customerId,
//    		@RequestParam(value = "documentId", required = true) Long id,
//            @RequestParam String status,
//            RedirectAttributes redirectAttributes) {
//        try {
//        	Customer customer = customerService.getCustomer(customerId);
//        	
//            if (customer != null) {
//            	customer.setStatus(status);
//            	customerService.updateDetails(customerId, customer);
//                kycDocumentService.updateDocumentStatus(id, status);
//                // If approved, create account
//                if ("APPROVED".equalsIgnoreCase(status)) {
//                    Account account = new Account();
//                    account.setCustomerId(customerId);
//                    account.setAccountNumber(AccountUtil.generateAccountNumber());
//                    account.setAccountType("SAVINGS");
//                    account.setBalance(BigDecimal.ZERO);
//                    account.setCurrency("INR");
//                    account.setStatus("ACTIVE");
//                    account.setOpenedDate(LocalDateTime.now());
//                    account.setCreatedAt(LocalDateTime.now());
//                    account.setUpdatedAt(LocalDateTime.now());
//                    accountdao.save(account);
//                }
//            }
//            
//            
//            
//            redirectAttributes.addFlashAttribute("success", "Status updated successfully");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Error updating status");
//        }
//        return "redirect:/kyc/admin";
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
////    ****************************************************************************************
//    
////    // Admin Dashboard
////    @GetMapping("/dashboard")
////    public String showAdminDashboard(Model model) {
////        try {
////            // Get dashboard statistics
////            ResponseEntity<Map> statsResponse = restTemplate.getForEntity(
////                API_GATEWAY_URL + "/admin/api/dashboard/stats", Map.class);
////            model.addAttribute("stats", statsResponse.getBody());
////            
////            // Get recent customers
////            ResponseEntity<List> customersResponse = restTemplate.getForEntity(
////                API_GATEWAY_URL + "/admin/api/customers", List.class);
////            List<Map> customers = customersResponse.getBody();
////            model.addAttribute("recentCustomers", customers != null && customers.size() > 5 ? 
////                customers.subList(0, 5) : customers);
////            
////            // Get recent KYC documents
////            ResponseEntity<List> kycResponse = restTemplate.getForEntity(
////                API_GATEWAY_URL + "/admin/api/kyc", List.class);
////            List<Map> kycDocs = kycResponse.getBody();
////            model.addAttribute("recentKYC", kycDocs != null && kycDocs.size() > 5 ? 
////                kycDocs.subList(0, 5) : kycDocs);
////            
////        } catch (Exception e) {
////            model.addAttribute("error", "Unable to load dashboard data: " + e.getMessage());
////        }
////        return "admin-dashboard-ui";
////    }
////    
////    // Customer Management
////    @GetMapping("/customers")
////    public String showAllCustomers(Model model) {
////        try {
////            ResponseEntity<List> response = restTemplate.getForEntity(
////                API_GATEWAY_URL + "/admin/api/customers", List.class);
////            model.addAttribute("customers", response.getBody());
////        } catch (Exception e) {
////            model.addAttribute("error", "Unable to fetch customers: " + e.getMessage());
////        }
////        return "admin-customers-ui";
////    }
////    
////    @GetMapping("/customers/{customerId}")
////    public String showCustomerDetails(@PathVariable String customerId, Model model) {
////        try {
////            ResponseEntity<Map> response = restTemplate.getForEntity(
////                API_GATEWAY_URL + "/admin/api/customers/" + customerId, Map.class);
////            model.addAttribute("customer", response.getBody());
////        } catch (Exception e) {
////            model.addAttribute("error", "Customer not found: " + e.getMessage());
////        }
////        return "admin-customer-details-ui";
////    }
////    
////    @PostMapping("/customers/update-status")
////    public String updateCustomerStatus(@RequestParam String customerId,
////                                     @RequestParam String status,
////                                     RedirectAttributes redirectAttributes) {
////        try {
////            Map<String, String> request = new HashMap<>();
////            request.put("status", status);
////            
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.APPLICATION_JSON);
////            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
////            
////            restTemplate.exchange(API_GATEWAY_URL + "/admin/api/customers/" + customerId + "/status",
////                HttpMethod.PUT, entity, Map.class);
////            redirectAttributes.addFlashAttribute("success", "Customer status updated successfully");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", "Failed to update customer status: " + e.getMessage());
////        }
////        return "redirect:/admin/customers";
////    }
////    
////    @PostMapping("/customers/update-kyc-status")
////    public String updateKycStatus(@RequestParam String customerId,
////                                @RequestParam String status,
////                                RedirectAttributes redirectAttributes) {
////        try {
////            Map<String, String> request = new HashMap<>();
////            request.put("status", status);
////            
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.APPLICATION_JSON);
////            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
////            
////            restTemplate.exchange(API_GATEWAY_URL + "/admin/api/customers/" + customerId + "/kyc-status",
////                HttpMethod.PUT, entity, Map.class);
////            redirectAttributes.addFlashAttribute("success", "KYC status updated successfully");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", "Failed to update KYC status: " + e.getMessage());
////        }
////        return "redirect:/admin/customers";
////    }
////    
////    // Account Management
////    @GetMapping("/accounts")
////    public String showAllAccounts(Model model) {
////        try {
////            ResponseEntity<List> response = restTemplate.getForEntity(
////                API_GATEWAY_URL + "/admin/api/accounts", List.class);
////            model.addAttribute("accounts", response.getBody());
////        } catch (Exception e) {
////            model.addAttribute("error", "Unable to fetch accounts: " + e.getMessage());
////        }
////        return "admin-accounts-ui";
////    }
////    
////    // KYC Management
////    @GetMapping("/kyc")
////    public String showAllKYC(Model model) {
////        try {
////            ResponseEntity<List> response = restTemplate.getForEntity(
////                API_GATEWAY_URL + "/admin/api/kyc", List.class);
////            model.addAttribute("kycDocuments", response.getBody());
////        } catch (Exception e) {
////            model.addAttribute("error", "Unable to fetch KYC documents: " + e.getMessage());
////        }
////        return "admin-kyc-ui";
////    }
////    
////    @GetMapping("/kyc/{id}")
////    public String showKYCDetails(@PathVariable Long id, Model model) {
////        try {
////            ResponseEntity<Map> response = restTemplate.getForEntity(
////                API_GATEWAY_URL + "/admin/api/kyc/" + id, Map.class);
////            model.addAttribute("kycDocument", response.getBody());
////        } catch (Exception e) {
////            model.addAttribute("error", "KYC document not found: " + e.getMessage());
////        }
////        return "admin-kyc-details-ui";
////    }
////    
////    @PostMapping("/kyc/update-status")
////    public String updateKYCStatus(@RequestParam Long id,
////                                @RequestParam String status,
////                                RedirectAttributes redirectAttributes) {
////        try {
////            Map<String, String> request = new HashMap<>();
////            request.put("status", status);
////            
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.APPLICATION_JSON);
////            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
////            
////            restTemplate.exchange(API_GATEWAY_URL + "/admin/api/kyc/" + id + "/status",
////                HttpMethod.PUT, entity, Map.class);
////            redirectAttributes.addFlashAttribute("success", "KYC status updated successfully");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", "Failed to update KYC status: " + e.getMessage());
////        }
////        return "redirect:/admin/kyc";
////    }
////    
////    // Health check
////    @GetMapping("/health")
////    @ResponseBody
////    public String healthCheck() {
////        try {
////            String response = restTemplate.getForObject(API_GATEWAY_URL + "/health/admin", String.class);
////            return "Frontend UP - Admin Service: " + response;
////        } catch (Exception e) {
////            return "Frontend UP - Admin Service DOWN: " + e.getMessage();
////        }
////    }
//}
