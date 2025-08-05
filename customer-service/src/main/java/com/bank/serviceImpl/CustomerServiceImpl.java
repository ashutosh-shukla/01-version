package com.bank.serviceImpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dao.CustomerDao;
import com.bank.exception.CustomerNotFoundException;
import com.bank.model.Customer;
import com.bank.services.CustomerService;
import com.bank.utils.PasswordUtil;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer createCustomer(Customer customer) {
		if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}
		String encryptedPassword = PasswordUtil.encodePassword(customer.getPassword());
		customer.setPassword(encryptedPassword);
		customer.setStatus("PENDING");

		return customerDao.save(customer);
	}

	@Override
	public Customer updateDetails(String customerId, Customer updatedData) {
		Customer existingCustomer = customerDao.findById(customerId);
		if (existingCustomer == null) {
			throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
		}

		// Update fields using business method in Customer entity
		existingCustomer.updateDetails(updatedData.getFirstName(), updatedData.getLastName(), updatedData.getAddress(),
				updatedData.getPhoneNumber(), updatedData.getStatus());

		// Optionally update other fields if needed (status etc.)
		existingCustomer.setStatus(updatedData.getStatus());

		// Save the updapted customer in DB
		return customerDao.update(existingCustomer);
	}

	@Override
	public void changePassword(String customerId, String currentPassword, String newPassword) {
		Customer customer = customerDao.findById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
		}

		// Check current password matches (assuming passwords are encrypted)
		if (!PasswordUtil.matches(currentPassword, customer.getPassword())) {
			throw new RuntimeException("Current password is incorrect");
		}

		// Encrypt the new password
		String encryptedPassword = PasswordUtil.encodePassword(newPassword);

		// Update password using DAO
		customerDao.updatePassword(customerId, encryptedPassword);
	}

	@Override
	public Customer getCustomer(String customerId) {
		Customer customer = customerDao.findById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
		}
		return customer;
	}

	// This method is created by Ashutosh for updating customer details in dashboard
	@Override
	public void save(Customer customer) {
		customerDao.update(customer);
	}

	@Override
	public void changeEmail(String customerId, String newEmail) {
		Customer customer = customerDao.findById(customerId);
		customer.setEmail(newEmail);
		customer.setUpdatedAt(new Date());
		customerDao.update(customer);
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		Customer customer = customerDao.findByEmail(email);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found with email: " + email);
		}
		return customer;
	}
}
