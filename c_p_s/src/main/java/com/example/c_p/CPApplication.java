package com.example.c_p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.c_p.job.ExpirationDailyJob;

@SpringBootApplication
@EnableScheduling
public class CPApplication {

	public static void main(String[] args) {
		SpringApplication.run(CPApplication.class, args);
		System.out.println("spring is running!");
//		ExpirationDailyJob edj = new ExpirationDailyJob();
//		edj.removeExpierdCoupon();
		System.out.println("the end!");
	}

}
