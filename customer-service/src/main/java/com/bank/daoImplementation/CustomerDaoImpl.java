package com.bank.daoImplementation;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bank.dao.CustomerDao;
import com.bank.model.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Customer save(Customer customer) {
		customer.setCreatedAt(new Date());
		customer.setUpdatedAt(new Date());
		entityManager.persist(customer);
		entityManager.flush();
		return customer;
	}

	@Override
	public Customer update(Customer customer) {
		return entityManager.merge(customer);
	}

	@Override
	public Customer findById(String customerId) {
		return entityManager.find(Customer.class, customerId);
	}

	@Override
	public void updatePassword(String customerId, String newPassword) {
		Customer customer = entityManager.find(Customer.class, customerId);
		if (customer != null) {
			customer.setPassword(newPassword);
			customer.setUpdatedAt(new Date());
			entityManager.merge(customer);
		}
	}

	@Override
	public List<Customer> findAll() {
		String query = "SELECT c FROM Customer c";
		return entityManager.createQuery(query, Customer.class).getResultList();
	}

	@Override
	public Customer findByEmail(String email) {
		String query = "SELECT c FROM Customer c WHERE c.email = :email";
		List<Customer> customers = entityManager.createQuery(query, Customer.class)
				.setParameter("email", email)
				.getResultList();
		return customers.isEmpty() ? null : customers.get(0);
	}

}
