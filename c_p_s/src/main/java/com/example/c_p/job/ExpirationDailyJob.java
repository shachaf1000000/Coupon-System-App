package com.example.c_p.job;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.c_p.beans.Coupon;
import com.example.c_p.repository.CouponRepository;

/**
 * This method is Autowired, it runs on start of the program (with 15 seconds
 * intial delay), and re-checks every fixed amount of time (24 Hours) It's
 * entire purpose is to check if the coupon is expired; it checks if the endDate
 * has passed; and if the coupon has been used prior.
 */
@Component

public class ExpirationDailyJob {
	@Autowired
	private CouponRepository couponsRepo;

	public ExpirationDailyJob() {
		super();

	}

//	@Override
//	public void run() {
//		while(!quit) {
//			for (Coupon cpn : couponsRepo.findByEndDateBefore(new Date(Calendar.getInstance().getTime().getTime()))) {
//				couponsRepo.delete(cpn);
//			}
//			try {
//				Thread.sleep(60000);
//			} catch (InterruptedException e) {
//				System.out.println(("Exception in ExpirationDailyJob - " + e.getMessage()));
//			}
//		}
//	}
	//
	@Scheduled(fixedRate = 86_400, initialDelay = 15_000)
	public void removeExpierdCoupon() {
		List<Coupon> couponToDelete = couponsRepo
				.findByEndDateBefore(new Date(Calendar.getInstance().getTime().getTime()));
		try {
			for (Coupon cpn : couponToDelete) {
				couponsRepo.delete(cpn);
				System.out.println(cpn+" deleted");
			}
		} catch (Exception e) {
			System.out.println(("Exception in ExpirationDailyJob - " + e.getMessage()));
		}
	}
}
