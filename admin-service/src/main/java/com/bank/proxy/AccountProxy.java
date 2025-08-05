package com.bank.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bank.dto.AccountRequest;
import com.bank.model.Account;
import com.bank.model.Customer;

// url = "http://localhost:8082/account-api"
@FeignClient(name = "ACCOUNT-SERVICE", path = "/account-api")
public interface AccountProxy {

	 @GetMapping("/customer/{customerId}")
	   List<Account> getAccountsByCustomerId(@PathVariable("customerId") String customerId);
	 
	
	 
	 @PostMapping("/create-acc/{customerId}")
	 Account createAccount(@PathVariable String customerId);

}
