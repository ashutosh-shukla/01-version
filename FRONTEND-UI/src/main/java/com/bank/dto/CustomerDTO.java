package com.bank.dto;

import java.util.Date;

public class CustomerDTO {

	private String customerId;

	private String firstName;

	private String lastName;

	private String email;

	private String phoneNumber;

	private String address;

	private String dateOfBirth;

	private String status;

	private Date createdAt;

	private Date updatedAt;

	private String password;

	protected void onCreate() {
		createdAt = new Date();
		updatedAt = new Date();
	}

	protected void onUpdate() {
		updatedAt = new Date();
	}

	// Default constructor
	public CustomerDTO() {
	}

	public void updateDetails(String firstName, String lastName, String address, String phoneNumber, String status) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setAddress(address);
		this.setPhoneNumber(phoneNumber);
		this.setStatus(status);
	}

	// Getters and Setters
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getPassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

}
