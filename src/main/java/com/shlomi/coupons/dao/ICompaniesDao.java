package com.shlomi.coupons.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shlomi.coupons.beans.Company;

public interface ICompaniesDao extends CrudRepository<Company, Long> {
	
	@Query("SELECT c FROM Company c")
	public List<Company> getAllCompanies();

}
