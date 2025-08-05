package com.bank.dao;



import java.util.List;
import java.util.Optional;

import com.bank.model.Customer;



public interface CustomerDao {
      Customer save(Customer customer);
      List<Customer> findAll();  //this is for admin future use
      Customer update(Customer customer);
      Customer findById(String customerId);
      void updatePassword(String customerId, String newPassword);
      Customer findByEmail(String email);
      

}
