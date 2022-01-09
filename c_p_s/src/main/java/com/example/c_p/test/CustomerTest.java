package com.example.c_p.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.c_p.beans.Category;
import com.example.c_p.beans.Company;
import com.example.c_p.beans.Coupon;
import com.example.c_p.maneger.ClientType;
import com.example.c_p.maneger.LogInManeger;
import com.example.c_p.service.CustomerService;

@Component
@Order(3)
public class CustomerTest implements CommandLineRunner {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private LogInManeger loginManager;
	@Override
	public void run(String... args) throws Exception {
		boolean status =testLoginCustomer(); 
		if (status) {

			testPurchaseCoupon();
			testGetCustomerCoupons();
			testGetCustomerCouponsByCategory();
			testGetCustomerCouponsByMaxPrice();
			
		}

		System.out.println(" Testings for Customer test has ended as the status is " + status);
		
	}
	public boolean testLoginCustomer() {
		try {
			ClientType type1 = ClientType.CUSTOMER;
			String email1 = "ElonMusk@gmail.com";
			String password1 = "Elon1234";
			CustomerService service1 =(CustomerService)loginManager.login(email1, password1, type1);
			if (service1 != null) {
				return true;
			}
			
//			ClientType type2 = ClientType.CUSTOMER;
//			String email2 = "AkioToyoda@gmail.com";
//			String password2 = "Akio1234";
//			CustomerService service2 =(CustomerService)loginManager.login(email2, password2, type2);
//			if (service2 != null) {
//				return true;
//			}
//
//			ClientType type3 = ClientType.CUSTOMER;
//			String email3 = "RajeevChaba@gmail.com";
//			String password3 = "Rajeev1234";
//			CustomerService service3 =(CustomerService)loginManager.login(email3, password3, type3);
//			if (service3 != null) {
//				return true;
//			}
//			
//			ClientType type4 = ClientType.CUSTOMER;
//			String email4 = "DieterZetsche@gmail.com";
//			String password4 = "Dieter1234";
//			CustomerService service4 =(CustomerService)loginManager.login(email4, password4, type4);
//			if (service4 != null) {
//				return true;
//			}
//			
//			ClientType type5 = ClientType.CUSTOMER;
//			String email5 = "LucaMeo@gmail.com";
//			String password5 = "Luca1234";
//			CustomerService service5 =(CustomerService)loginManager.login(email5, password5, type5);
//			if (service5 != null) {
//				return true;
//			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public void testGetCustomerCouponsByCategory() {
		try {
			customerService.getCustomerCouponsByCategory(Category.RESTAURANT);
			System.out.println("Here are your Company's Coupons selected by the Category(RESTAURANT) : "+customerService.getCustomerCouponsByCategory(Category.RESTAURANT));
			customerService.getCustomerCouponsByCategory(Category.ELECTRICITY);
			System.out.println("Here are your Company's Coupons selected by the Category(ELECTRICITY)"+customerService.getCustomerCouponsByCategory(Category.ELECTRICITY));
			customerService.getCustomerCouponsByCategory(Category.FOOD);
			System.out.println("Here are your Company's Coupons selected by the Category(FOOD)"+customerService.getCustomerCouponsByCategory(Category.FOOD));
			customerService.getCustomerCouponsByCategory(Category.VACATION);
			System.out.println("Here are your Company's Coupons selected by the Category(VACATION)"+customerService.getCustomerCouponsByCategory(Category.VACATION));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void testGetCustomerCoupons() {
		try {
			customerService.getCustomerCoupons();
			System.out.println("Here are all the Coupons that were purchased by " + customerService.getCustomerDetails());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void testGetCustomerCouponsByMaxPrice() {
		try {
			double x = (Math.random()*1000);
			customerService.getCustomerCouponsByMaxPrice(x);
			System.out.println("Here are your Company's Coupons selected by your (max price)"+customerService.getCustomerCouponsByMaxPrice(x));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testPurchaseCoupon() {
		try {
			java.util.Date utilDate = new java.util.Date();

			java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
			java.sql.Date sqlDate1Plus = new java.sql.Date(utilDate.getTime() + 500000);
			
			java.sql.Date sqlDate2 = new java.sql.Date(utilDate.getTime()+ 1000000);
			java.sql.Date sqlDate2Plus = new java.sql.Date(utilDate.getTime() + 1500000);
			
			java.sql.Date sqlDate3 = new java.sql.Date(utilDate.getTime()+ 1500000);
			java.sql.Date sqlDate3Plus = new java.sql.Date(utilDate.getTime() + 2000000);
			
			java.sql.Date sqlDate4 = new java.sql.Date(utilDate.getTime()+ 2000000);
			java.sql.Date sqlDate4Plus = new java.sql.Date(utilDate.getTime() + 2500000);
			
			java.sql.Date sqlDate5 = new java.sql.Date(utilDate.getTime()+ 2500000);
			java.sql.Date sqlDate5Plus = new java.sql.Date(utilDate.getTime() + 3000000);
			
			Company comp1 = new Company("Tesla", "Tesla@gmail.com", "Tesla1234");
			comp1.setId(1);
			
			
			Coupon Coup2 = new Coupon(comp1, "LG TV", "25% off on your next purchase", Category.ELECTRICITY, sqlDate2,
					sqlDate2Plus, 2, 242.0, "nothing");
			Coupon Coup3 = new Coupon(comp1, "free shake", "1 free drink", Category.FOOD, sqlDate3,
					sqlDate3Plus, 2, 12.0, "nothing");
			
			
			Coup2.setId(2);
			System.out.println("The coupon "+ Coup2.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup2);
			Coup3.setId(3);
			System.out.println("The coupon "+ Coup3.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup3);
			
			Company comp2 = new Company("Toyota", "Toyota@gmail.com", "Toyota1234");
			comp2.setId(2);
			
			
			Coupon Coup8 = new Coupon(comp2, "Red Meat", "15% Off on one kilo of any red meat", Category.FOOD, sqlDate3,
					sqlDate3Plus, 2, 18.0, "nothing");
			Coupon Coup9 = new Coupon(comp2, "1 day trip to Amsterdam ", "a couple size bedroom,and free dinner", Category.VACATION, sqlDate4,
					sqlDate4Plus, 2, 412.0, "nothing");
			
			
			Coup8.setId(8);
			System.out.println("The coupon "+ Coup8.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup8);
			Coup9.setId(9);
			System.out.println("The coupon "+ Coup9.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup9);
			
			Company comp3 = new Company("MG", "MG@gmail.com", "MG1234");
			comp3.setId(3);
			
			
			Coupon Coup14 = new Coupon(comp3, "1 day trip to sinay ", "a couple size bedroom,and free dinner", Category.VACATION, sqlDate4,
					sqlDate4Plus, 2, 112.0, "nothing");
			Coupon Coup15 = new Coupon(comp3, "1 day trip to sinay*2 ", "a couple size bedroom,and free dinner*2", Category.VACATION, sqlDate5,
					sqlDate5Plus, 2, 224.0, "nothing");
			
			Coup14.setId(14);
			System.out.println("The coupon "+ Coup14.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup14);
			Coup15.setId(15);
			System.out.println("The coupon "+ Coup15.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup15);
			
			Company comp4 = new Company("Mercedes", "Mercedes@gmail.com", "Mercedes1234");
			comp4.setId(4);
			
			
			Coupon Coup16 = new Coupon(comp4, "Catering ", "All you can eat (150 Mains)", Category.RESTAURANT, sqlDate1,
					sqlDate1Plus, 2, 129.0, "nothing");
			Coupon Coup20 = new Coupon(comp4, "1 day trip to berlin*2 ", "a couple size bedroom,and free dinner*2", Category.VACATION, sqlDate5,
					sqlDate5Plus, 2, 624.0, "nothing");
			
			Coup20.setId(20);
			System.out.println("The coupon "+ Coup20.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup20);
			Coup16.setId(16);
			System.out.println("The coupon "+ Coup16.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup16);
			
			Company comp5 = new Company("Renault", "Renault@gmail.com", "Renault1234");
			comp5.setId(5);
			
			
			Coupon Coup21 = new Coupon(comp5, "Chef meal ", "Chef meal with all expenses on us", Category.RESTAURANT, sqlDate1,
					sqlDate1Plus, 2, 100.0, "nothing");
			Coupon Coup22 = new Coupon(comp5, "One Plus Phone", "15% Off and a free screen protector", Category.ELECTRICITY, sqlDate2,
					sqlDate2Plus, 2, 292.0, "nothing");
			
			
			Coup21.setId(21);
			System.out.println("The coupon "+ Coup21.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup21);
			Coup22.setId(22);
			System.out.println("The coupon "+ Coup22.getTitle()+"has been purchased successfully!");
			customerService.purchaseCoupon(Coup22);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
