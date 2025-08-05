package com.bank.auth.proxy;

import com.bank.auth.dto.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "customer-service", url = "${customer-service.url}")
public interface CustomerProxy {
    
    @PostMapping("/customer/register")
    ResponseEntity<Object> registerCustomer(@RequestBody RegisterRequest request);
    
    @GetMapping("/customer/email/{email}")
    ResponseEntity<Object> getCustomerByEmail(@PathVariable String email);
    
    @GetMapping("/customer/{customerId}")
    ResponseEntity<Object> getCustomerById(@PathVariable String customerId);
}