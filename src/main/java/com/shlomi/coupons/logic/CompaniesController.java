package com.shlomi.coupons.logic;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shlomi.coupons.beans.Company;
import com.shlomi.coupons.dao.ICompaniesDao;
import com.shlomi.coupons.enums.ErrorType;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.utils.Utils;

@Controller
public class CompaniesController {

	@Autowired
	private ICompaniesDao companiesDao;


	public CompaniesController() {

	}

	public long createCompany(Company company) throws ApplicationException {
		// Validation
		if (company == null) {
			throw new ApplicationException(ErrorType.INVALID_COMPANY, "A null company");
		}

		if(company.getCompanyName() == "") {
			throw new ApplicationException(ErrorType.INVALID_COMPANY_NAME,"Blank company name");
		}

		if(!Utils.isEmailValid(company.getEmail())) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL,"Invalid email address");
		}

		if(!Utils.isPhoneNumberValid(company.getPhoneNumber())) {
			throw new ApplicationException(ErrorType.INVALID_PHONE_NUMBER,"Invalid phone number");
		}

		try {
			this.companiesDao.save(company);
			return company.getId();
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COMPANY,"General Error");
		}

	}

	public void removeCompany(long companyId) throws ApplicationException {

		try {
			Company company = getCompany(companyId);
			companiesDao.delete(company);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COMPANY,"General Error");
		}
	}

	public Company getCompany(long companyId)  throws ApplicationException {
		
		try {
			Company company = companiesDao.findById(companyId).get();
			return company;
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COMPANY,"General Error");
		}
	}

	public void updateCompany(Company company) throws ApplicationException {
	
		try {
			this.companiesDao.save(company);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COMPANY,"General Error");
		}
	}

	public List<Company> getAllCompanies() throws ApplicationException {
		
		
		try {
			return this.companiesDao.getAllCompanies();
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COMPANY,"General Error");
		}
	}

	public Company getCompanyByName(String name) throws ApplicationException {
		try {
			Company company = companiesDao.getCompanyByName(name);
			return company;
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COMPANY,"Company not found.");
		}
	}
}

