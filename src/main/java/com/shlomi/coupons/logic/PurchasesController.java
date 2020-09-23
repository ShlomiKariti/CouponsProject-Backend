package com.shlomi.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shlomi.coupons.beans.Purchase;
import com.shlomi.coupons.dao.IPurchasesDao;
import com.shlomi.coupons.enums.ErrorType;
import com.shlomi.coupons.exceptions.ApplicationException;

@Controller
public class PurchasesController {

	@Autowired
	private IPurchasesDao purchasesDao;
	
	@Autowired
	private CouponsController couponsController;

	public PurchasesController() {

	}

	public void createPurchase(Purchase purchase) throws ApplicationException {
		//Validation
		if (purchase == null) {
			throw new ApplicationException(ErrorType.INVALID_PURCHASE,"A null purchase");
		}

		//		if(!couponsController.isCouponInStock(purchase.getCouponId())) {
		//			throw new ApplicationException(ErrorType.OUT_OF_STOCK,"Out of stock");
		//		}

		if(purchase.getAmount()<=0) {
			throw new ApplicationException(ErrorType.INVALID_AMOUNT_OF_COUPONS,"Invalid amount has been selected");
		}

		try {
			this.purchasesDao.save(purchase);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_PURCHASE,"General Error");
		}
	}

	public void removePurchase(long id) throws ApplicationException {

		try {
			Purchase purchase = getPurchase(id);
			purchasesDao.delete(purchase);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_PURCHASE,"General Error");
		}
	}

	public Purchase getPurchase(long id)  throws ApplicationException {

		try {
			Purchase purchase = purchasesDao.findById(id).get();
			return purchase;
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_PURCHASE,"General Error");
		}
	}

	public List<Purchase> getAllPurchasesByUserID(long userId) throws ApplicationException {

		try {
			return this.purchasesDao.getAllPurchasesByUserID(userId);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_PURCHASE,"General Error");
		}

	}

	public List<Purchase> getAllPurchasesByCouponID(long couponId) throws ApplicationException {
		try {
			return this.purchasesDao.getAllPurchasesByCouponID(couponId);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_PURCHASE,"General Error");
		}
	}
}

