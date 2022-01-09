package com.example.c_p.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.c_p.beans.Category;
import com.example.c_p.beans.Company;
import com.example.c_p.beans.Coupon;
import com.example.c_p.beans.Customer;
import com.example.c_p.exception.CustomException;

@Service
@Scope("prototype")
@Transactional
public class CompanyService extends ClientService {

	private int companyId;

	public CompanyService() {
		super();

	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public CompanyService(int companyId) {

		this.companyId = companyId;
	}

	@Override
	public boolean login(String email, String password) throws CustomException {
		if (email != null && password != null) {

			Company id = companiesRepo.findByEmailAndPassword(email, password);

			if ( id != null) {
				System.out.println("Welcome " + id.getName() + "!");
				companyId = id.getId();
				return true;
			} else {
				throw new CustomException("Invalid email or password");
			}
		}

		return false;
	}

	public void addCoupon(Coupon coupon) throws CustomException {

		if (coupon != null) {
			companyId = coupon.getCompany().getId();
			// Check if the date is after current time
			checkdate((Date) coupon.getStartDate());
			// Check if the start date before end date
			checkdate((Date) coupon.getEndDate());
			if (coupon.getEndDate() != null && coupon.getStartDate().after(coupon.getEndDate())) {
				throw new CustomException("Cannot add coupon with invalid date range");
			}

			if (!companiesRepo.existsById(coupon.getCompany().getId()) || companyId != coupon.getCompany().getId()) {
				throw new CustomException("Cannot add coupon because no company found with this specific Id");
			} else {
				if (couponsRepo.existsByTitleAndCompanyId(coupon.getTitle(), coupon.getCompany().getId())) {
					throw new CustomException("The title is already exist in the company");
				} else {
					couponsRepo.save(coupon);
					System.out.println("the coupon titled " + coupon.getTitle() + " was added successfully!");
				}
			}

		} else {
			throw new CustomException("Cannot add empty Coupon");
		}
	}

	public void updateCoupon(Coupon coupon) throws CustomException {
		List<Coupon> check = couponsRepo.findByIdAndCompanyId(coupon.getId(), coupon.getCompany().getId());
		if (check.isEmpty()) {
			throw new CustomException("The coupon does not exist in the system");
		}

		// check if the date after current time
		checkdate((Date) coupon.getStartDate());
		// Check if the start date before end date
		checkdate((Date) coupon.getEndDate());
		if (coupon.getStartDate().after(coupon.getEndDate())) {
			throw new CustomException("Cannot update coupon with invalid date range");
		}
		if (coupon.getAmount() < 0) {
			throw new CustomException("Coupon's amount cannot be lower than zero");
		}
		if (coupon.getPrice() <= 0) {
			throw new CustomException("Coupon's Price cannot be lower than zero");
		}
		couponsRepo.save(coupon);
		System.out.println("the coupon " + coupon.getId() + " was updated successfully ");
	}

	public void deleteCoupon(int couponId) throws CustomException {
		if (couponId > 0) {
			Coupon c = couponsRepo.findById(couponId);
			if (c != null) {
				System.out.println("The coupon with an id of: " + couponId + " has been successfully deleted.");
				couponsRepo.delete(c);
			} else {
				throw new CustomException("The coupon does not exist in the system");
			}
		} else {
			throw new CustomException("Cannot delete coupon with invalid Id");
		}
	}

	public List<Coupon> getCompanyCouponsByCompanyId() throws CustomException {

		return couponsRepo.findByCompanyId(companyId);
	}

	public List<Coupon> getCompanyCouponsByCategory(Category category) throws CustomException {
		return couponsRepo.findByCategoryAndCompanyId(category, companyId);
	}

	public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws CustomException {
		//		List<Coupon>companyCoupons=getCompanyDetails().getCoupons();
		//		companyCoupons.retainAll(couponsRepo.findByPriceLessThanEqual(maxPrice));
		return couponsRepo.findByPriceLessThanEqualAndCompany_Id(maxPrice, companyId);
//				couponsRepo.findByMaxPrice(maxPrice, companyId);
	}

	public Company getCompanyDetails() throws CustomException {
		Company c = companiesRepo.findById(companyId);
		if (c == null) {
			throw new CustomException("Company does not exists with this id");
		}
		return c;
	}

	
	// Check if the date is after the current date
	private void checkdate(Date date) throws CustomException {
		if (date != null) {
			Date currentDate = Date.valueOf(LocalDate.now());

			if (date.before(currentDate)) {
				throw new CustomException("The date have to be after the current time");
			}
		} else {
			throw new CustomException("The date have to be valid");
		}
	}
}
