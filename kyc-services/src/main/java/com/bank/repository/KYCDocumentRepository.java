package com.bank.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.model.KYCDocument;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
@Transactional
public interface KYCDocumentRepository extends JpaRepository<KYCDocument, Long> {
	
	 List<KYCDocument> findByStatus(String status);

	    @Query("SELECT k FROM KYCDocument k ORDER BY k.createdAt DESC")
	    List<KYCDocument> findAllOrderByCreatedAtDesc();

	    boolean existsByCustomerId(String customerId);

	    
	    boolean existsByAadharNumber(String aadharNumber);

	    boolean existsByPanNumber(String panNumber);
}
