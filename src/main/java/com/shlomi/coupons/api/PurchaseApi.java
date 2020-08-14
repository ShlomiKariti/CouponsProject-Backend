package com.shlomi.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shlomi.coupons.beans.Purchase;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.logic.PurchasesController;

public class PurchaseApi {

	@RestController
	@RequestMapping("/purchase")
	public class PurchasesApi {

		@Autowired
		private PurchasesController purchasesController;


		@PostMapping
		public void createPurchase(@RequestBody Purchase purchase) throws ApplicationException {
			this.purchasesController.createPurchase(purchase);
		}

		@DeleteMapping("/{id}")
		public void removePurchase(@PathVariable("id") long id) throws ApplicationException {
			this.purchasesController.removePurchase(id);

		}
		@GetMapping("/{id}")
		public Purchase getPurchaseByPurchaseID(@PathVariable("id")long id) throws ApplicationException {
			return this.purchasesController.getPurchase(id);

		}
		@GetMapping("/customer/{customer_id}")
		public List<Purchase> getAllPurchasesByCustomerID(@RequestParam("customer_id")long customerId) throws ApplicationException {
			return this.purchasesController.getAllPurchasesByCustomerID(customerId);
		}

		@GetMapping("/coupon/{coupon_id}")
		public List<Purchase> getAllPurchasesByCouponID(@RequestParam("coupon_id")long couponId) throws ApplicationException {
			return this.purchasesController.getAllPurchasesByCouponID(couponId);

		}
	}
}