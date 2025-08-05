package com.bank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "kyc_documents")
public class KYCDocument {
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kyc_seq")
	@SequenceGenerator(name = "kyc_seq", sequenceName = "kyc_sequence", allocationSize = 1)
	private Long id;

	@Column(name = "customer_id", length = 60, unique = true, nullable = false)
	private String customerId;

	@NotBlank(message = "Aadhar number is required")
	@Size(max = 12, message = "Aadhar number must not exceed 12 characters")
	@Column(name = "aadhar_number", nullable = false, length = 100)
	private String aadharNumber;

	@NotBlank(message = "Pan number is required")
	@Size(max = 10, message = "Aadhar number must not exceed 10 characters")
	@Column(name = "pan_number", nullable = false, length = 100)
	private String panNumber;

	@Lob
	@Column(name = "aadhar_front", columnDefinition = "CLOB")
	private String aadharFront;

	@Lob
	@Column(name = "aadhar_back", columnDefinition = "CLOB")
	private String aadharBack;

	@Lob
	@Column(name = "pan_front", columnDefinition = "CLOB")
	private String panFront;

	@Lob
	@Column(name = "pan_back", columnDefinition = "CLOB")
	private String panBack;

	@Lob
	@Column(name = "photograph", columnDefinition = "CLOB")
	private String photograph;

	@Column(name = "status", length = 20)
	private String status = "PENDING";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
		updatedAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new Date();
	}

	// Constructors
	public KYCDocument() {
	}
	
	

	public KYCDocument(String customerId,
			@NotBlank(message = "Aadhar number is required") @Size(max = 12, message = "Aadhar number must not exceed 12 characters") String aadharNumber,
			@NotBlank(message = "Pan number is required") @Size(max = 10, message = "Aadhar number must not exceed 10 characters") String panNumber) {
		super();
		this.customerId = customerId;
		this.aadharNumber = aadharNumber;
		this.panNumber = panNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharFront() {
		return aadharFront;
	}

	public void setAadharFront(String aadharFront) {
		this.aadharFront = aadharFront;
	}

	public String getAadharBack() {
		return aadharBack;
	}

	public void setAadharBack(String aadharBack) {
		this.aadharBack = aadharBack;
	}

	public String getPanFront() {
		return panFront;
	}

	public void setPanFront(String panFront) {
		this.panFront = panFront;
	}

	public String getPanBack() {
		return panBack;
	}

	public void setPanBack(String panBack) {
		this.panBack = panBack;
	}

	public String getPhotograph() {
		return photograph;
	}

	public void setPhotograph(String photograph) {
		this.photograph = photograph;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
