package com.shlomi.coupons.logic;

import java.util.Base64;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shlomi.coupons.beans.PostLoginData;
import com.shlomi.coupons.beans.SuccessfulLoginData;
import com.shlomi.coupons.beans.User;
import com.shlomi.coupons.beans.UserLoginDetails;
import com.shlomi.coupons.dao.IUsersDao;
import com.shlomi.coupons.enums.ErrorType;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.utils.Utils;

@Controller
public class UsersController {

	@Autowired
	private IUsersDao usersDao;

	@Autowired
	private CacheController cacheController;


	public UsersController() {

	}

	public Long createUser(User user) throws ApplicationException{
		// Validation
		if (user == null) {
			throw new ApplicationException(ErrorType.INVALID_USER,"A null user");
		}

		if (user.getPassword().equals("")) {
			throw new ApplicationException(ErrorType.INVALID_PASSWORD,"An empty password");
		}

		if (user.getPassword().length() < 6) {
			throw new ApplicationException(ErrorType.INVALID_PASSWORD,"Password is too short");
		}

		if (user.getPassword().length() > 10) {
			throw new ApplicationException(ErrorType.INVALID_PASSWORD,"Password is too long");
		}

		if (user.getUsername().length() < 2) {
			throw new ApplicationException(ErrorType.INVALID_USER,"User name is too short");
		}

		//		if (this.usersDao.isUsernameExists(user.getUsername())) {
		//			throw new ApplicationException(ErrorType.INVALID_USER,"The username is not available, please choose a different one");
		//		}
		byte[] hashedPassword = Utils.hashPassword(user.getPassword());
		Base64.Encoder enc = Base64.getEncoder();
		user.setPassword(enc.encodeToString(hashedPassword));

		try {
			this.usersDao.save(user);
			return user.getId();
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_USER,"General Error");
		}
	}

	public void removeUser(long id) throws ApplicationException {

		try {
			User user = getUser(id);
			usersDao.delete(user);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_USER,"General Error");
		}
	}

	public User getUser(long id)  throws ApplicationException {

		try {
			User user = usersDao.findById(id).get();
			return user;
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_USER,"General Error");
		}
	}

	public void updateUser(User user) throws ApplicationException {

		try {
			this.usersDao.save(user);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_USER,"General Error");
		}
	}

	public SuccessfulLoginData login(UserLoginDetails userLoginDetails) throws ApplicationException {

		User user = this.usersDao.findByUsername(userLoginDetails.getUsername());
		if (user == null) {
			throw new ApplicationException(ErrorType.FAILED_LOGIN, "Invalid username or password");
		}
		if(!this.comparePassword(user.getPassword(), userLoginDetails.getPassword())) {
			throw new ApplicationException(ErrorType.FAILED_LOGIN, "Invalid username or password");
		}

		String token = Utils.generateToken();
		PostLoginData postLoginData = new PostLoginData(user.getId(), user.getCompanyId(), user.getType());
		cacheController.put(token, postLoginData);
		return new SuccessfulLoginData(token, postLoginData.getUserType());

	}

	public List<User> getAllUsers() throws ApplicationException {

		try {
			return this.usersDao.getAllUsers();
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_USER,"General Error");
		}
	}

	public List<User> getAllUsersByCompanyID(long companyId) throws ApplicationException {

		try {
			return this.usersDao.getAllUsersByCompanyID(companyId);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_USER,"General Error");
		}
	}

	public void changePassword(String username, String oldPassword, String newPassword) throws ApplicationException {

		User user = usersDao.findByUsername(username);

		if(user == null) {
			throw new ApplicationException(ErrorType.INVALID_USER, "User has not been found");
		}
		if(!this.comparePassword(user.getPassword(), oldPassword)) {
			throw new ApplicationException(ErrorType.INVALID_PASSWORD, "Password does not match");
		}

		byte[] hashedPassword = Utils.hashPassword(newPassword);

		Base64.Encoder enc = Base64.getEncoder();
		user.setPassword(enc.encodeToString(hashedPassword));
		this.usersDao.save(user);
	}

	private boolean comparePassword(String existingPassword, String userInput) {

		byte[] userInputHash = Utils.hashPassword(userInput);
		Base64.Encoder enc = Base64.getEncoder();
		return existingPassword.equals(enc.encodeToString(userInputHash));

	}

	public PostLoginData getPostLoginDataByToken(String token) throws ApplicationException {

		PostLoginData postLoginData = cacheController.get(token);
		if(postLoginData == null) {
			throw new ApplicationException(ErrorType.GENERAL_ERROR, "User is not logged in");
		}
		return postLoginData;
		
	}

	
	
}
