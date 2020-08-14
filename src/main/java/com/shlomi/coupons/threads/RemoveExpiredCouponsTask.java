package com.shlomi.coupons.threads;

import java.util.TimerTask;

import com.shlomi.coupons.exceptions.ApplicationException;
import com.shlomi.coupons.logic.CouponsController;

public class RemoveExpiredCouponsTask extends TimerTask  {

	private CouponsController couponsController;

	public RemoveExpiredCouponsTask(CouponsController couponsController) {
		this.couponsController = couponsController;
	}

	@Override
	public void run()  {			
		try {
			couponsController.removeExpiredCoupons();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
