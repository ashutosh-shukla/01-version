package com.bank.services;


import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.KYCDocument;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(String customerId);

    List<Account> getAccountsByCustomerId(String customerId);

    List<KYCDocument> getKycDocuments(String customerId);
    
    String approveKyc(String customerId);

    String rejectKyc(String customerId);

	List<KYCDocument> getAllDocuments();

	Customer getCustomer(String customerId);

	Optional<KYCDocument> getDocumentById(Long id);

	void deleteDocument(Long id);

	void updateDetails(String customerId, Customer customer);

	void updateDocumentStatus(Long id, String status);


}
