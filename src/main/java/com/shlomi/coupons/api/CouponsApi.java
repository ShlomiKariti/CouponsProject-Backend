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

import com.shlomi.coupons.beans.Coupon;
import com.shlomi.coupons.enums.Category;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.logic.CouponsController;


@RestController
@RequestMapping("/coupon")
public class CouponsApi {

	@Autowired
	private CouponsController couponsController;

	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		this.couponsController.createCoupon(coupon);

	}
	@PutMapping
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		this.couponsController.updateCoupon(coupon);
	}

	@GetMapping("/{id}")
	public Coupon getCoupon(@PathVariable("id") long id) throws ApplicationException {
		return this.couponsController.getCoupon(id);
	}

	@DeleteMapping("/{id}")
	public void removeCoupon(@PathVariable("id") long id) throws ApplicationException {
		this.couponsController.removeCoupon(id);
	}

	@GetMapping
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return this.couponsController.getAllCoupons();
	}
	
	@GetMapping("/byCategory/{category}")
	public List<Coupon> findByCategory(@PathVariable("category")String category) throws ApplicationException {
		Category categoryName = Category.valueOf(category.toUpperCase());
		return this.couponsController.findByCategory(categoryName);

	}
	
	@GetMapping("/byCompany/{company_id}")
	public List<Coupon> getAllCouponsByCompanyId(@PathVariable("company_id")long companyID) throws ApplicationException {
		return this.couponsController.getAllCouponsByCompanyId(companyID);
	}

}