package com.bank.model;

import java.util.Date;

public class KYCDocument {

	private Long id;

	private String customerId;

	private String aadharNumber;

	private String panNumber;

	private String aadharFront;

	private String aadharBack;

	private String panFront;

	private String panBack;

	private String photograph;

	private String status = "PENDING";

	private Date createdAt;

	private Date updatedAt;

	protected void onCreate() {
		createdAt = new Date();
		updatedAt = new Date();
	}

	protected void onUpdate() {
		updatedAt = new Date();
	}

	// Constructors
	public KYCDocument() {
	}

	public KYCDocument(String customerId, String aadharNumber, String panNumber) {
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
