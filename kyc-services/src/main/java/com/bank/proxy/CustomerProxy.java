package com.bank.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bank.model.Customer;

@FeignClient(name="customer-service" , path="/customers")
public interface CustomerProxy {
	
	// http://CUSTOMER-SERVICE/customers/{customerId}
	 @GetMapping("/{customerId}")
	 public Customer getCustomer(@PathVariable String customerId);
	        
	    

}



