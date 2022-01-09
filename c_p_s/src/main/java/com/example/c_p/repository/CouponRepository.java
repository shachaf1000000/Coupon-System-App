package com.example.c_p.repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.c_p.beans.Category;
import com.example.c_p.beans.Coupon;
import com.example.c_p.beans.Customer;

/**
 * CouponRepository is a data access logic which defines the data access to
 * Coupons and which manipulation can be performed with the Coupon type
 * 
 * @function findById allows to user to find a Coupon by ID, @returns a list of
 *           Coupons by that ID
 * @function getAllCouponsCustomer allows to user to get all the Coupons a
 *           Customer has with the specific coupon ID, @returns a Coupon List
 * @function findByCategoryAndCompanyId allows to user to find a Coupon by it's
 *           category Enum and the ID of the possessing company, returns a
 *           Coupon List
 * @function findByMaxPrice allows to user to find the highest costing (double)
 *           a specific Company has, @returns a list of the highest costing
 *           Coupons
 * @function existsByTitleAndCompanyId allows to user to check if the coupon
 *           exists by checking its title AND correlation to the company's
 *           ID, @returns T/F
 * @function existsByTitleAndId allows to user to check if the coupon exists by
 *           checking its title AND using it's specific ID (in case of multiple
 *           titles of identical string), @returns T/F
 * @function findCategoryCustomer allows to user to check for a coupon list
 *           linked to a customer by ID, and using the Coupon's category
 *           (Enum), @returns Coupon List
 * @function findMaxPriceCustomer allows to user to check for the highest priced
 *           Coupon linked to a Customer by ID, @returns a Coupon List
 * @function findByEndDateBefore allows to user to check for all the coupons
 *           that are available (are not expired), @returns a Coupon List
 */
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	public Coupon findById(int id);

	@Query("SELECT c FROM Customer c WHERE c.id IN (SELECT c.id FROM c.coupons cust WHERE cust.id = ?1)")
	public List<Coupon> getAllCouponsCustomer(int coupon_id);

	public List<Coupon> findByCategoryAndCompanyId(Category category, int companyId);

	@Query("select e from Coupon e where e.price <= ?1 and id = ?2")
	public List<Coupon> findByMaxPrice(double price, int companyId);
	public List<Coupon> findByPriceLessThanEqualAndCompany_Id(double price, int companyId);
	public Boolean existsByTitleAndCompanyId(String title, int companyId);

	@Query("select e.id from Coupon e where e.title=?1 and e.id=?2")
	public Boolean existsByTitleAndId(String title, int couponId);

	@Query("select e from Coupon e join e.customers c where c.id =?1 and e.category = ?2")
	public List<Coupon> findCategoryCustomer(int cust_id, Category category);

	@Query("select e from Coupon e join e.customers c where c.id =?1 and e.price <= ?2")
	public List<Coupon> findByPriceCustomer(int cust_id, double maxPrice);

	public List<Coupon> findByEndDateBefore(Date endDate);
	
	public List<Coupon> findByIdAndCompanyId(int id,int companyId);

	public List<Coupon> findByCompanyId(int companyId);

	public List<Coupon> findByPriceAndCompanyId(double maxPrice, int companyId);

	public List<Coupon> findByCategory(Category category);

	public List<Coupon> findByPriceLessThanEqual(double maxPrice);
}
