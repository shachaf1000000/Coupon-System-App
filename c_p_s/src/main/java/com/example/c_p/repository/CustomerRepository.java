package com.example.c_p.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.c_p.beans.Coupon;
import com.example.c_p.beans.Customer;
/**
 *@function findByEmailAndPassword allows the user to find a Customer by Email and Password, @returns the Customer entity.
 *@function existsByEmail allows the user to check if the user exists by Email, @returns T/F.
 *@function findById allows the user to find a Customer by ID, @returns the Customer entity.
 *@function findCustomerByEmailAndId allows the user to find a Customer by Email and ID, @returns the Customer entity
 *@function findCustomerCoupon allows the user to find a Coupon linked to a Customer by Coupon ID and Customer ID, @returns a Coupon entity
 *@function findCoupon allows the user to find a Coupon List by a Customers ID, @returns A Coupon List.

 */

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//	@Query("select id from Customer where email = ?1 and password =?2")
	public Customer findByEmailAndPassword(String email,String password);

	public boolean existsByEmail(String email);

	public Customer findById(int id);

	@Query("select c from Customer c where c.email = ?1 and c.id <> ?2")
	public Customer findCustomerByEmailAndId(String email , int id);

	@Query("SELECT coup FROM Coupon coup WHERE coup.id IN (SELECT coup.id FROM coup.customers cust WHERE cust.id = ?1 and coup.id = ?2)")
	public Coupon findCustomerCoupon(int custId, int cpnId);
	
	@Query("SELECT coup FROM Coupon coup WHERE coup.id Not IN (SELECT coup.id FROM coup.customers cust WHERE cust.id=?1) and coup.amount > 0 and coup.endDate > CURDATE() and coup.company.getId() > 0")
	public List<Coupon> findCoupon(int cust_id);
	
	public String findByEmail(String email);
	
	public String findByPassword(String password);

//	public int findCoupon(int customerId);
}
