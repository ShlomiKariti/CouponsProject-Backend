package com.shlomi.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shlomi.coupons.beans.Company;
import com.shlomi.coupons.beans.Coupon;
import com.shlomi.coupons.beans.PostLoginData;
import com.shlomi.coupons.beans.User;
import com.shlomi.coupons.dataobjects.CouponDataObject;
import com.shlomi.coupons.enums.Category;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.logic.CouponsController;
import com.shlomi.coupons.logic.UsersController;


@RestController
@RequestMapping("/coupon")
public class CouponsApi {

	@Autowired
	private CouponsController couponsController;
	

	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon, @RequestAttribute("userData") PostLoginData postLoginData) throws ApplicationException {
		coupon.setCompany(new Company());
		coupon.getCompany().setId(postLoginData.getCompanyId());
		this.couponsController.createCoupon(coupon);

	}
	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
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
	public List<CouponDataObject> getAllCoupons() throws ApplicationException {
		return this.couponsController.getAllCoupons();
	}

	@GetMapping("/byCategory/{category}")
	public List<Coupon> findByCategory(@PathVariable("category")String category) throws ApplicationException {
		Category categoryName = Category.valueOf(category.toUpperCase());
		return this.couponsController.findByCategory(categoryName);

	}

	@GetMapping("/byCompany")
	public List<Coupon> getAllCouponsByCompanyId(@RequestAttribute("userData") PostLoginData postLoginData) throws ApplicationException {
		return this.couponsController.getAllCouponsByCompanyId(postLoginData.getCompanyId());
	}

}