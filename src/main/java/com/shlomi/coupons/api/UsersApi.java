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

import com.shlomi.coupons.beans.ChangePasswordDetails;
import com.shlomi.coupons.beans.PostLoginData;
import com.shlomi.coupons.beans.SuccessfulLoginData;
import com.shlomi.coupons.beans.User;
import com.shlomi.coupons.beans.UserLoginDetails;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.logic.UsersController;


@RestController
@RequestMapping("/user")
public class UsersApi {

	@Autowired
	private UsersController usersController;

	@PostMapping
	public void createUser(@RequestBody User user) throws ApplicationException {
		this.usersController.createUser(user);
	}

	@PutMapping
	public void updateUser(User user) throws ApplicationException {
		this.usersController.updateUser(user);
	}

	@DeleteMapping("/{id}")
	public void removeUser(@PathVariable("id") long id) throws ApplicationException {
		this.usersController.removeUser(id);

	}
	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") long id) throws ApplicationException {
		return this.usersController.getUser(id);

	}
	@PostMapping("/login")
	public SuccessfulLoginData login(@RequestBody UserLoginDetails userLoginDetails) throws ApplicationException {
		return this.usersController.login(userLoginDetails);
	}
	@GetMapping
	public List<User> getAllUsers() throws ApplicationException {
		return this.usersController.getAllUsers();
	}
	@GetMapping("/byCompany/{companyId}")
	public List<User> getAllUsersByCompanyID(@PathVariable("company_id")long companyId) throws ApplicationException {
		return this.usersController.getAllUsersByCompanyID(companyId);
	}
	
	@PutMapping("/changePassword")
	public void changePassword(@RequestBody ChangePasswordDetails changePasswordDetails) throws ApplicationException {
		this.usersController.changePassword(changePasswordDetails.getUsername(), changePasswordDetails.getOldPassword(), changePasswordDetails.getNewPassword());
	}

}

