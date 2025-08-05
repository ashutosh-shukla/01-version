package com.bank.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.bank.model.KYCDocument;

//  url = "http://localhost:8083/kyc/api"
@FeignClient(name = "KYC-SERVICE", path = "/kyc/api")
public interface KYCProxy {

	 @GetMapping("/customer/{customerId}")
	    List<KYCDocument> getDocumentsByCustomerId(@PathVariable("customerId") Long id);
	
	 @GetMapping("/documents/status/{status}")
	    List<KYCDocument> getDocumentsByStatus(@PathVariable("status") String status);

	 @PutMapping("/documents/{id}/verify")
	 KYCDocument verifyKyc(@PathVariable Long id);

	 void rejectKyc(Long kycId);

	 void deleteDocument(Long kycId);

	 List<KYCDocument> getDocumentsByCustomerId(String customerId);


}






