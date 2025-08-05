package com.bank.services;

import java.util.List;

import com.bank.model.Customer;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    Customer updateDetails(String customerId, Customer customer);
  
    Customer getCustomer(String customerId);

    void save(Customer customer);  //this is created by ashutosh for updating customer details in dashboard

    void changePassword(String customerId, String currentPassword, String newPassword);
    void changeEmail(String customerId, String newEmail);
    Customer getCustomerByEmail(String email);

}
