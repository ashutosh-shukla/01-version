package com.bank.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.bank.model.KYCDocument;

public interface KYCDocumentService {

	public List<KYCDocument> getAllDocuments();

	public Optional<KYCDocument> getDocumentById(Long id);

	public List<KYCDocument> getDocumentsByStatus(String status);

	public KYCDocument saveDocument(KYCDocument document);

	public KYCDocument saveKycDocument(String customerId, String aadharNumber, String panNumber,
			MultipartFile aadharFront, MultipartFile aadharBack, MultipartFile panFront, MultipartFile panBack,
			MultipartFile photograph) throws IOException;

	public void updateDocumentStatus(Long id, String status);

	public void deleteDocument(Long id);

}
