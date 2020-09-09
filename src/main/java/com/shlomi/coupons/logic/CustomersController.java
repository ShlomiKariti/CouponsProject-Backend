package com.shlomi.coupons.logic;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shlomi.coupons.beans.Customer;
import com.shlomi.coupons.dao.ICustomersDao;
import com.shlomi.coupons.enums.ErrorType;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.utils.Utils;

@Controller
public class CustomersController {

	@Autowired
	private ICustomersDao customersDao;


	public CustomersController() {

	}

	public void createCustomer(Customer customer) throws ApplicationException {
		// Validation
		if (customer == null) {
			throw new ApplicationException(ErrorType.INVALID_CUSTOMER,"A null customer");
		}

		if(customer.getName() == "") {
			throw new ApplicationException(ErrorType.INVALID_CUSTOMER,"Blank name");
		}


		if(!Utils.isEmailValid(customer.getEmail())) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL,"Invalid email address");
		}

		byte[] hashedPassword = Utils.hashPassword(customer.getPassword());
		Base64.Encoder enc = Base64.getEncoder();
		customer.setPassword(enc.encodeToString(hashedPassword));
		
		try {
			this.customersDao.save(customer);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_CUSTOMER,"General Error");
		}
	}

	public void removeCustomer(long id) throws ApplicationException {

		try {
			Customer customer = getCustomer(id);
			customersDao.delete(customer);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_CUSTOMER,"General Error");
		}
	}


	public Customer getCustomer(long companyId) throws ApplicationException {

		try {
			Customer customer = customersDao.findById(companyId).get();
			return customer;
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_CUSTOMER,"General Error");
		}
	}

	public void updateCustomer(Customer customer) throws ApplicationException {

		try {
			this.customersDao.save(customer);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_CUSTOMER,"General Error");
		}
	}

	public List<Customer> getAllCustomers() throws ApplicationException {

		try {
			return this.customersDao.getAllCustomers();
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_CUSTOMER,"General Error");
		}
	}

	public List<Customer> getAllCustomersByMinAge(int minAge) throws ApplicationException {

		try {
			return this.customersDao.getAllCustomersByMinAge(minAge);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_CUSTOMER,"General Error");
		}
	}
	
}





