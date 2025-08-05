//package com.bank.serviceImpl;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.bank.model.Account;
//import com.bank.model.Customer;
//import com.bank.model.KYCDocument;
//import com.bank.proxy.AccountProxy;
//import com.bank.proxy.CustomerProxy;
//import com.bank.proxy.KYCProxy;
//import com.bank.services.AdminService;
////import com.bank.util.EmailUtil;
//import com.bank.dto.AccountRequest;
//@Service
//public class AdminServiceImpl implements AdminService {
//
//	 	@Autowired
//	    private CustomerProxy customerProxy;
//
//	    @Autowired
//	    private AccountProxy accountProxy;
//
//	    @Autowired
//	    private KYCProxy kycProxy;
//	    
////	    @Autowired
////	    private EmailUtil emailUtil;
//	
//	@Override
//	public List<Customer> getAllCustomers() {
//		return customerProxy.getAllCustomers();		
//	}
//
//	@Override
//	public Customer getCustomerById(String customerId) {
//		return customerProxy.getCustomerById(customerId);
//	}
//
//	@Override
//	public List<Account> getAccountsByCustomerId(String customerId) {
//		return accountProxy.getAccountsByCustomerId(customerId);
//	}
//
//	@Override
//	public List<KYCDocument> getKycDocuments(String customerId) {
//		return kycProxy.getDocumentsByCustomerId(customerId);
//	}
//	
//	@Override
//	public String approveKyc(String customerId) {
//		
//		// Step 1: Get customer
//        Customer customer = customerProxy.getCustomerById(customerId);
//        if (customer == null) {
//            throw new RuntimeException("Customer not found");
//        }
//
//        if ("VERIFIED".equalsIgnoreCase(customer.getStatus())) {
//            return "Customer is already KYC verified.";
//        }
//
//        // Step 2: Update customer status
//        customer.setStatus("VERIFIED");
//        customer.setUpdatedAt(new Date());
//        customerProxy.updateCustomer(customerId, customer);
//
//        // Step 3: Create account via proxy
//        AccountRequest accountRequest = new AccountRequest();
//        accountRequest.setCustomerId(customerId);
//        accountRequest.setAccountType("SAVINGS");
//        accountRequest.setBalance(0.0);
//        Account account = accountProxy.createAccount(accountRequest);
//
//        return "KYC verified and account created successfully. Account No: " + account.getAccountNumber();
//    }
//	
//	/*
//	    // Update status
//	    customerProxy.updateKycStatus(customerId, "VERIFIED");
//	
//	    // Create account for the customer
//	    accountProxy.createAccount(customerId);
//	
//	    // Send email
//	    Customer customer = customerProxy.getCustomerById(customerId);
//	    String subject = "KYC Approved - Welcome to MyBank!";
//	    String body = "Dear " + customer.getFirstName() + ",\n\n"
//	            + "Your KYC has been successfully verified. "
//	            + "Your account has been created and is now active.\n\nThank you for choosing us.";
//	    emailUtil.sendEmail(customer.getEmail(), subject, body);
//	
//	    return "KYC approved and account created for customer ID: " + customerId;
//	}
//	*/
//
//	@Override
//	public String rejectKyc(String customerId) {
//	    // Update status
//	    customerProxy.updateKycStatus(customerId, "REJECTED");
//	
//	    // Send email
////	    Customer customer = customerProxy.getCustomerById(customerId);
////	    String subject = "KYC Rejected - Action Required";
////	    String body = "Dear " + customer.getFirstName() + ",\n\n"
////	            + "Unfortunately, your KYC verification failed due to invalid documents. "
////	            + "Please re-upload valid KYC documents to proceed further.\n\nRegards,\nMyBank Support";
////	    emailUtil.sendEmail(customer.getEmail(), subject, body);
//	
//	    return "KYC rejected for customer ID: " + customerId;
//	}
//
//	@Override
//	public List<KYCDocument> getAllDocuments() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Customer getCustomer(String customerId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Optional<KYCDocument> getDocumentById(Long id) {
//		// TODO Auto-generated method stub
//		return Optional.empty();
//	}
//
//	@Override
//	public void deleteDocument(Long id) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateDetails(String customerId, Customer customer) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateDocumentStatus(Long id, String status) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//	
//}