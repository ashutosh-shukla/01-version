//package com.bank.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.bank.model.Account;
//import com.bank.model.Customer;
//import com.bank.model.KYCDocument;
//import com.bank.services.AdminService;
//import com.bank.proxy.AccountProxy;
//import com.bank.proxy.CustomerProxy;
//import com.bank.proxy.KYCProxy;
////import com.bank.proxy.NotificationProxy;
//import com.bank.dto.AccountRequest;
////import com.bank.dto.EmailRequest;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private AccountProxy accountProxy;
//    
//    @Autowired
//    private CustomerProxy customerProxy;
//    
//    @Autowired
//    private KYCProxy kycProxy;
//    
//    @Autowired
//    private Account account;
//    
//    
////    @GetMapping("/admin")
////    public String showAdminPanel(Model model) {
////        List<KycDocument> documents = kycDocumentService.getAllDocuments();
////
////        // Extract unique customerIds from documents
////        Set<String> customerIds = documents.stream()
////                                           .map(KycDocument::getCustomerId)
////                                           .collect(Collectors.toSet());
////
////        // Fetch customer details for all customerIds
////        Map<String, Customer> customerMap = new HashMap<>();
////
////        for (String customerId : customerIds) {
////            try {
////                Customer customer = customerService.getCustomer(customerId); // Implement this method accordingly
////                if (customer != null) {
////                    customerMap.put(customerId, customer);
////                }
////            } catch (Exception e) {
////                // log error and continue
////            }
////        }
////
////        model.addAttribute("documents", documents);
////        model.addAttribute("customerMap", customerMap); // Add map to model for JSP lookup
////
////        return "admin-panel-simple";
////    }
//    
//    
//    
////    @GetMapping("/admin/view/{id}")
////    public String viewDocument(@PathVariable Long id, Model model) {
////        Optional<KycDocument> documentOpt = kycDocumentService.getDocumentById(id);
////        if (documentOpt.isPresent()) {
////            KycDocument document = documentOpt.get();
////            model.addAttribute("document", document);
////
////            try {
////                String customerId = document.getCustomerId();
////                // URL to call customer service - replace with correct URL or service discovery
//////                String customerServiceUrl = "http://customer-service/customers/api/by-id/" + customerId;
////
////                Customer customer = customerService.getCustomer(customerId);
////                model.addAttribute("customer", customer);
////            } catch (Exception e) {
////                // Log error or handle customer fetch failure gracefully
////                model.addAttribute("customer", null);
////            }
////
////            return "document-view";
////        } else {
////            return "redirect:/kyc/admin?error=Document not found";
////        }
////    }
////    
//    
//    
//    @PostMapping("/admin/update-status")
//    public String updateStatus(@RequestParam(value = "customerId", required = true) String customerId,
//    		@RequestParam(value = "documentId", required = true) Long id,
//            @RequestParam String status,
//            RedirectAttributes redirectAttributes) {
//        try {
//        	Customer customer = customerProxy.getCustomer(customerId);
//        	
//            if (customer != null) {
//            	customer.setStatus(status);
//            	
//            	customerProxy.updateCustomer(customerId, customer);
//                kycDocumentService.updateDocumentStatus(id, status);
//                // If approved, create account
//                if ("APPROVED".equalsIgnoreCase(status)) {
//                       accountProxy.createAccount(customerId);
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
//    @PostMapping("/admin/delete/{id}")
//    public String deleteDocument(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        try {
//            kycDocumentService.deleteDocument(id);
//            redirectAttributes.addFlashAttribute("success", "Document deleted successfully");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Error deleting document");
//        }
//        return "redirect:/kyc/admin";
//    }
//    
//    
//    
////    @Autowired
////    private NotificationProxy notificationProxy;
//
// // Dashboard
////    @GetMapping("/dashboard")
////    public String dashboard(Model model) {
////        List<Customer> customers = adminService.getAllCustomers();
////        model.addAttribute("customers", customers);
////        return "admin-panel-simple";
////    }
////
////    @GetMapping("/view")
////    public String viewCustomer(@RequestParam String customerId, Model model) {
////        Customer customer = adminService.getCustomerById(customerId);
////        List<Account> accounts = adminService.getAccountsByCustomerId(customerId);
////        List<KYCDocument> kycDocs = adminService.getKycDocuments(customerId);
////
////        model.addAttribute("customer", customer);
////        model.addAttribute("accounts", accounts);
////        model.addAttribute("kycDocs", kycDocs);
////
////        return "admin-panel-simple";
////    }
////
////    @PostMapping("/approve-kyc")
////    public String approveKyc(@RequestParam String customerId, RedirectAttributes redirectAttributes) {
////        try {
////            String result = adminService.approveKyc(customerId);
////
//////            // Create account
//////            Account account = new Account();
//////            account.setCustomerId(customerId);
//////            account.setAccountNumber(UUID.randomUUID().toString().substring(0, 12));
//////            account.setAccountType("SAVINGS");
//////            account.setBalance(BigDecimal.valueOf(1000));
//////            account.setCurrency("INR");
//////            account.setStatus("ACTIVE");
//////            account.setOpenedDate(LocalDateTime.now());
//////            account.setCreatedAt(LocalDateTime.now());
//////            account.setUpdatedAt(LocalDateTime.now());
//////
//////             Account createdAccount = accountProxy.createAccount(account);
////
////            // Step 3: Create account via proxy
////            AccountRequest accountRequest = new AccountRequest();
////            accountRequest.setCustomerId(customerId);
////            accountRequest.setAccountType("SAVINGS");
////            accountRequest.setBalance(0.0);
////            Account createdAccount = accountProxy.createAccount(accountRequest);
////
////    
////            // Send Email Notification
////            Customer customer = adminService.getCustomer(customerId);
//////            EmailRequest email = new EmailRequest();
//////            email.setTo(customer.getEmail());
//////            email.setSubject("KYC Verified and Account Created");
//////            email.setBody("Dear " + customer.getFirstName() + ",\n\nYour KYC has been successfully verified.\n" +
//////                          "Account Number: " + createdAccount.getAccountNumber() + "\n" +
//////                          "Balance: " + createdAccount.getBalance() + "\n\nWelcome to our bank!\n\nRegards,\nBank Team");
//////
//////            notificationProxy.sendEmail(email);
////
////            redirectAttributes.addFlashAttribute("message", result);
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", "Failed to approve KYC and create account.");
////        }
////        return "redirect:/admin/dashboard";
////    }
////    
////    
////
////    @PostMapping("/reject-kyc")
////    public String rejectKyc(@RequestParam String customerId, RedirectAttributes redirectAttributes) {
////        String result = adminService.rejectKyc(customerId);
////        redirectAttributes.addFlashAttribute("message", result);
////        return "redirect:/admin/dashboard";
////    }
////
////    @GetMapping("/verify-kyc/{customerId}")
////    public String verifyKyc(@PathVariable("customerId") String customerId, RedirectAttributes redirectAttributes) {
////        return approveKyc(customerId, redirectAttributes);
////    }
////
////    @GetMapping("/admin/viewcust")
////    public String showAdminPanel(Model model) {
////        List<KYCDocument> documents = adminService.getAllDocuments();
////        Set<String> customerIds = documents.stream().map(KYCDocument::getCustomerId).collect(Collectors.toSet());
////        Map<String, Customer> customerMap = new HashMap<>();
////
////        for (String customerId : customerIds) {
////            try {
////                Customer customer = adminService.getCustomer(customerId);
////                if (customer != null) customerMap.put(customerId, customer);
////            } catch (Exception ignored) {}
////        }
////
////        model.addAttribute("documents", documents);
////        model.addAttribute("customerMap", customerMap);
////        return "admin-panel-simple";
////    }
////
////    @GetMapping("/admin/view/{id}")
////    public String viewDocument(@PathVariable Long id, Model model) {
////        Optional<KYCDocument> documentOpt = adminService.getDocumentById(id);
////        if (documentOpt.isPresent()) {
////            KYCDocument document = documentOpt.get();
////            model.addAttribute("document", document);
////            try {
////                Customer customer = adminService.getCustomer(document.getCustomerId());
////                model.addAttribute("customer", customer);
////            } catch (Exception e) {
////                model.addAttribute("customer", null);
////            }
////            return "document-view";
////        }
////        return "redirect:/kyc/admin?error=Document not found";
////    }
////
////    @PostMapping("/admin/update-status")
////    public String updateStatus(@RequestParam String customerId,
////                               @RequestParam Long documentId,
////                               @RequestParam String status,
////                               RedirectAttributes redirectAttributes) {
////        try {
////            Customer customer = adminService.getCustomer(customerId);
////            customer.setStatus(status);
////            adminService.updateDetails(customerId, customer);
////            adminService.updateDocumentStatus(documentId, status);
////            redirectAttributes.addFlashAttribute("success", "Status updated successfully");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", "Error updating status");
////        }
////        return "redirect:/kyc/admin";
////    }
////
////    @PostMapping("/admin/delete/{id}")
////    public String deleteDocument(@PathVariable Long id, RedirectAttributes redirectAttributes) {
////        try {
////            adminService.deleteDocument(id);
////            redirectAttributes.addFlashAttribute("success", "Document deleted successfully");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", "Error deleting document");
////        }
////        return "redirect:/kyc/admin";
////    }
////
////    @GetMapping("/view-application")
////    public String viewApplication(@RequestParam Long documentId, Model model) {
////        Optional<KYCDocument> documentOpt = adminService.getDocumentById(documentId);
////        if (documentOpt.isPresent()) {
////            KYCDocument document = documentOpt.get();
////            model.addAttribute("document", document);
////            try {
////                Customer customer = adminService.getCustomer(document.getCustomerId());
////                model.addAttribute("customer", customer);
////            } catch (Exception e) {
////                model.addAttribute("customer", null);
////            }
////            return "customerwithkyc";
////        }
////        return "redirect:/kyc/upload?error=Document not found";
////    }
////    
////    @GetMapping("/admin/view/{id}")
////    public String viewKycAndCustomerDetails(@PathVariable Long id, Model model) {
////       
////        KYCDocument document = kycProxy.verifyKyc(id);
////        Customer customer = customerProxy.getCustomerById(document.getCustomerId());
////        
////        model.addAttribute("document", document);
////        model.addAttribute("customer", customer);
////        return "kyc-view";
////    }
////    
////    
//// // 🔹 2. View KYC Details (from dashboard)
////    @GetMapping("/kyc/{id}")
////    public String viewKycDetails(@PathVariable("customerId") Long id, Model model) {
////        List<KYCDocument> document = kycProxy.getDocumentsByCustomerId(id);
////        model.addAttribute("doc", document);
////        return "viewkyc"; // maps to viewkyc.jsp
////    }
////
////    // 🔹 3. View Customer Info
////    @GetMapping("/customer/{customerId}")
////    public String viewCustomerDetails(@PathVariable("customerId") String customerId, Model model) {
////        Customer customer = customerProxy.getCustomerById(customerId);
////        model.addAttribute("customer", customer);
////        return "customerinfo"; // maps to customerinfo.jsp
////    }
////
////    // 🔹 4. Approve KYC and Trigger Account Creation
////    @PostMapping("/kyc/approve/{id}")
////    public String approveKYC(@PathVariable("id") Long kycId, Model model) {
////        // 1. Approve KYC
////        KYCDocument updatedDoc = kycProxy.verifyKyc(kycId);
////
////        // 2. Get Customer
////        Customer customer = customerProxy.getCustomerById(updatedDoc.getCustomerId());
////        
////
////        // 3. Create Account
////        Account newAccount = new Account();
//////        newAccount.setAccountType("SAVINGS");
//////        newAccount.setBalance(0.0);
//////        newAccount.setCustomerId(customer.getCustomerId());
//////        newAccount.setStatus("ACTIVE");
//////        Account createdAccount = accountProxy.createAccount(newAccount);
////        
////        AccountRequest accountRequest = new AccountRequest();
////        accountRequest.setCustomerId(customer.getCustomerId());
////        accountRequest.setAccountType("SAVINGS");
////        accountRequest.setBalance(0.0);
////        Account createdAccount = accountProxy.createAccount(accountRequest);
////
////        // 4. Send Notification
//////        String emailMessage = "Dear " + customer.getFirstName()
//////                + ",\n\nYour account has been successfully created.\nAccount ID: " + createdAccount.getAccountId();
//////        notificationProxy.sendEmail(customer.getEmail(), "Account Created", emailMessage);
////
////        model.addAttribute("account", createdAccount);
////        return "accountcreated"; // maps to accountcreated.jsp
////    }
////
////    // 🔹 5. Reject KYC
////    @PostMapping("/kyc/reject/{id}")
////    public String rejectKYC(@PathVariable("id") Long kycId) {
////        kycProxy.rejectKyc(kycId);
////        return "redirect:/kyc/admin/dashboard";
////    }
////
////    // 🔹 6. Delete KYC
////    @PostMapping("/kyc/delete/{id}")
////    public String deleteKYC(@PathVariable("id") Long kycId) {
////        kycProxy.deleteDocument(kycId);
////        return "redirect:/kyc/admin/dashboard";
////    }
////
////    // 🔹 7. View error page (optional)
////    @GetMapping("/error")
////    public String errorPage(@RequestParam(required = false) String message, Model model) {
////        model.addAttribute("message", message != null ? message : "Something went wrong.");
////        return "error"; // maps to error.jsp
////    }
////
//}