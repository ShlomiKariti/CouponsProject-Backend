package com.shlomi.coupons.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shlomi.coupons.beans.Customer;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.logic.CustomersController;

@RestController
@RequestMapping("/customer")
public class CustomersApi {

	@Autowired
	private CustomersController customersController;

	@PostMapping
	public void createCustomer(@RequestBody Customer customer) throws ApplicationException {
		this.customersController.createCustomer(customer);

	}
	@PutMapping
	public void updateCustomer(Customer customer) throws ApplicationException {
		this.customersController.updateCustomer(customer);
	}

	@GetMapping("/{id}")
	public Customer getCustomer(@PathVariable("id") long id) throws ApplicationException {
		return this.customersController.getCustomer(id);
	}

	@DeleteMapping("/{id}")
	public void removeCustomer(@PathVariable("id") long id) throws ApplicationException {
		this.customersController.removeCustomer(id);
	}

	public List<Customer> getAllCustomers() throws ApplicationException {
		return this.customersController.getAllCustomers();
	}

	@GetMapping("/age/{age}")
	public List<Customer> getAllUsersByMinAge(@PathVariable("age") int minAge) throws ApplicationException {
		return this.customersController.getAllCustomersByMinAge(minAge);
	}
}