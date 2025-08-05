package com.bank.controller;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.proxy.AccountServiceProxy;
import com.bank.services.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountServiceProxy accountServiceProxy;

   

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.createCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/customer/register")
    public ResponseEntity<Object> registerCustomerForAuth(@RequestBody com.bank.auth.dto.RegisterRequest request) {
        try {
            Customer customer = new Customer();
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setEmail(request.getEmail());
            customer.setPhoneNumber(request.getPhoneNumber());
            customer.setAddress(request.getAddress());
            customer.setDateOfBirth(request.getDateOfBirth());
            customer.setPassword(request.getPassword());
            customer.setStatus("PENDING");
            
            Customer savedCustomer = customerService.createCustomer(customer);
            
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("customerId", savedCustomer.getCustomerId());
            response.put("message", "Customer registered successfully");
            response.put("success", true);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("message", "Registration failed: " + e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/customer/email/{email}")
    public ResponseEntity<Object> getCustomerByEmail(@PathVariable String email) {
        try {
            Customer customer = customerService.getCustomerByEmail(email);
            if (customer != null) {
                java.util.Map<String, Object> response = new java.util.HashMap<>();
                response.put("customerId", customer.getCustomerId());
                response.put("firstName", customer.getFirstName());
                response.put("lastName", customer.getLastName());
                response.put("email", customer.getEmail());
                response.put("phoneNumber", customer.getPhoneNumber());
                response.put("address", customer.getAddress());
                response.put("dateOfBirth", customer.getDateOfBirth());
                response.put("status", customer.getStatus());
                response.put("password", customer.getPassword());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String customerId) {
        try {
            Customer customer = customerService.getCustomer(customerId);
            if (customer != null) {
                java.util.Map<String, Object> response = new java.util.HashMap<>();
                response.put("customerId", customer.getCustomerId());
                response.put("firstName", customer.getFirstName());
                response.put("lastName", customer.getLastName());
                response.put("email", customer.getEmail());
                response.put("phoneNumber", customer.getPhoneNumber());
                response.put("address", customer.getAddress());
                response.put("dateOfBirth", customer.getDateOfBirth());
                response.put("status", customer.getStatus());
                response.put("password", customer.getPassword());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable String customerId) {
        return customerService.getCustomer(customerId);
    }

    @PutMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
        return customerService.updateDetails(customerId, customer);
    }

    @PutMapping("/{customerId}/change-password")
    public void changePassword(@PathVariable String customerId,
                               @RequestParam String currentPassword,
                               @RequestParam String newPassword) {
        customerService.changePassword(customerId, currentPassword, newPassword);
    }

    @PutMapping("/{customerId}/change-email")
    public void changeEmail(@PathVariable String customerId, @RequestParam String newEmail) {
        customerService.changeEmail(customerId, newEmail);
    }

    // --- Account API calls ---

    @GetMapping("/{customerId}/account")
    public Account getAccount(@PathVariable String customerId) {
        return accountServiceProxy.getAccountByCustomerId(customerId);
    }

    @PostMapping("/{customerId}/deposit")
    public void deposit(@PathVariable String customerId, @RequestParam BigDecimal amount) {
        accountServiceProxy.deposit(customerId, amount);
    }

    @PostMapping("/{customerId}/withdraw")
    public void withdraw(@PathVariable String customerId, @RequestParam BigDecimal amount) {
        accountServiceProxy.withdraw(customerId, amount);
    }

    @PostMapping("/{customerId}/transfer")
    public void transfer(@PathVariable String customerId,
                         @RequestParam String toAccountNumber,
                         @RequestParam BigDecimal amount) {
        accountServiceProxy.transfer(customerId, toAccountNumber, amount);
    }

    // --- Transaction API call ---

    @GetMapping("/{customerId}/transactions")
    public List<Transaction> getTransactionHistory(@PathVariable String customerId) {
        return accountServiceProxy.getTransactionHistory(customerId);
    }
}