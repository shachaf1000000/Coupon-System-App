package com.example.c_p.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.c_p.beans.Category;
import com.example.c_p.beans.Coupon;
import com.example.c_p.beans.Customer;
import com.example.c_p.exception.CustomException;
import com.example.c_p.repository.CouponRepository;
import com.example.c_p.repository.CustomerRepository;

/**
 * CustomerService is the business logic behind the functions a Customer is
 * allowed to perform, it has a personalized Login, and functions based on if
 * the login was successful
 * 
 * @function login is the personalized Login for the Customer clientType, it
 *           checks if the email and password are not null, and are a part of
 *           the Customer database, and allows functionality based if it is true
 * @function purchaseCoupon is a function that allows a Customer to purchase a
 *           Coupon, based on a Coupon object provided to it
 * @function getCustomerCouponsByCustomerId allows the user to get a list of
 *           Coupons based on the Customer ID , @returns an ArrayList of Coupons
 * @function getCustomerCouponsByCategory allows the user to get a list of
 *           Coupons based on the Coupon Category linked to the Customer
 *           , @returns an ArrayList of Coupons
 * @function getCustomerCouponsByMaxPrice
 * @function getCustomerDetails
 * @function getAllCoupons
 */

@Service
@Transactional
public class CustomerService extends ClientService {
	private int customerId;

	public CustomerService() {

	}

	@Override
	public boolean login(String email, String password) throws CustomException {
		if (email != null && password != null) {
			Customer cust = customersRepo.findByEmailAndPassword(email, password);
			System.out.println();
			if (cust != null) {
				customerId = cust.getId();
				System.out.println("Welcome " + cust.getFirstName() + "!");
				return true;
			} else {
				throw new CustomException("Invalid email or password");
			}
		}
		return false;

	}

	public void purchaseCoupon(Coupon coupon) throws CustomException {
		if (coupon != null) {
			Coupon c = (Coupon) couponsRepo.findById(coupon.getId());
			System.out.println(c);
			if (c != null) {
				if (customersRepo.findCustomerCoupon(customerId, c.getId()) != null) {
					throw new CustomException("The same coupon cannot be purchased more than once.");
				} else {
					if (c.getAmount() > 0) {
						if (c.getEndDate() != null
								&& c.getEndDate().getTime() <= Calendar.getInstance().getTime().getTime())
							throw new CustomException("This coupon cannot be purchased because it's expired.");
						else {
							Customer cus = customersRepo.findById(customerId);
							c.setAmount(c.getAmount() - 1);
							cus.addCoupon(c);
							customersRepo.save(cus);
						}
					} else {
						throw new CustomException("This coupon cannot be purchased because it's not in stock.");
					}
				}
			}

			else {
				throw new CustomException("No coupon found with this specific Id.");
			}
		} else {
			throw new CustomException("Cannot purchase empty coupon.");
		}
	}

	public List<Coupon> getCustomerCoupons() throws CustomException {
		return getCustomerDetails().getCoupons();
	}

	public List<Coupon> getCustomerCouponsByCategory(Category category) throws CustomException {
		List<Coupon> customerCoupons = getCustomerCoupons();
		customerCoupons.retainAll(couponsRepo.findByCategory(category));
		return customerCoupons;
	}
	public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws CustomException {
		List<Coupon> customerCoupons = getCustomerCoupons();
		customerCoupons.retainAll(couponsRepo.findByPriceLessThanEqual(maxPrice));
		return customerCoupons;
	}

	public Customer getCustomerDetails() throws CustomException {
		Customer c = customersRepo.findById(customerId);
		if (c == null) {
			throw new CustomException("Customer doesn't exists");
		}
		return c;
	}
	
	public ArrayList<Coupon> getAllCoupons() throws CustomException {
		if (customersRepo.findCoupon(customerId).isEmpty()) {
			throw new CustomException("No coupons found in the system");
		}
		return new ArrayList<Coupon>(customersRepo.findCoupon(customerId));
	}

}
