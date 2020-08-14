package com.shlomi.coupons.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shlomi.coupons.beans.Coupon;
import com.shlomi.coupons.dao.ICouponsDao;
import com.shlomi.coupons.enums.Category;
import com.shlomi.coupons.enums.ErrorType;
import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.threads.RemoveExpiredCouponsTask;

@Controller
public class CouponsController {
	
	@Autowired
	private ICouponsDao couponsDao;

	
	public CouponsController() {

	}

	@PostConstruct
	public void expiredCouponsTask() throws ApplicationException  {

		TimerTask timerTask = new RemoveExpiredCouponsTask(this);
		Timer timer = new Timer();
		Date nextMidnight = getNextMidnight();
		timer.schedule(timerTask, nextMidnight,  24*60*60*1000);

	}

	private Date getNextMidnight() {

		Calendar midnight = Calendar.getInstance();
		midnight.add(Calendar.DAY_OF_MONTH, 1);
		midnight.set(Calendar.HOUR_OF_DAY, 0);
		midnight.set(Calendar.MINUTE, 0);
		midnight.set(Calendar.SECOND, 0);

		return midnight.getTime();

	}

	public void createCoupon(Coupon coupon) throws ApplicationException {
		//Validation
		if (coupon == null) {
			throw new ApplicationException(ErrorType.INVALID_COUPON, "A null coupon");
		}

		if(coupon.getCategory()==null) {
			throw new ApplicationException(ErrorType.INVALID_CATEGORY,"Category has no been selected");
		}

		if(coupon.getTitle()=="") {
			throw new ApplicationException(ErrorType.INVALID_COUPON_TITLE,"No title");
		}

		if(coupon.getPrice()==0) {
			throw new ApplicationException(ErrorType.INVALID_PRICE,"No price input.");
		}
		try {
			this.couponsDao.save(coupon);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.GENERAL_ERROR,"General Error" );
		}
	}

	public void removeCoupon(long couponId) throws ApplicationException {

		try {
			Coupon coupon = getCoupon(couponId);
			couponsDao.delete(coupon);

		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COUPON,"General Error");
		}
	}

	public Coupon getCoupon(long couponId) throws ApplicationException {

		try {
			Coupon coupon = couponsDao.findById(couponId).get();
			return coupon;

		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COUPON,"General Error");
		}
	}

	public void updateCoupon(Coupon coupon) throws ApplicationException {
		try {
			this.couponsDao.save(coupon);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COUPON,"General Error");
		}
	}

	public List<Coupon> getAllCouponsByCompanyId(long companyID) throws ApplicationException {
		
		try {
			return this.couponsDao.getAllCouponsByCompanyId(companyID);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COUPON,"General Error");
		}
	}

	public void removeExpiredCoupons() throws ApplicationException{
		
		Date currentDate = Calendar.getInstance().getTime();
		couponsDao.removeExpiredCoupons(currentDate);
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {

		try {
			return this.couponsDao.getAllCoupons();
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COUPON,"General Error");
		}
	}

	public List<Coupon> findByCategory(Category category) throws ApplicationException {

		try {
			return this.couponsDao.findByCategory(category);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COUPON,"General Error");
		}
	}

}
