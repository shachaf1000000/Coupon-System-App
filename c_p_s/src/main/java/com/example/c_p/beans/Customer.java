package com.example.c_p.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @param id        is auto generated; and serves the functions to locate and
 *                  identify them
 * @param firstName defines a customer's name via String
 * @param lastName  defines a customer's last name via String
 * @param email     defines a customer's email via String
 * @param password  defines a password identifier to the Company via String
 * @param coupons   is a list of Coupons; for the purpose of listing which
 *                  Coupons are allocated to the specific Customer.
 */

@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private  String email;
	private  String password;
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER,mappedBy = "customers")
	List<Coupon> coupons = new ArrayList<Coupon>();
	
	public Customer(int id, String firstName, String lastName, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Customer( String firstName, String lastName, String email, String password,
			ArrayList<Coupon> coupons) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}

	public Customer() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public  String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public  String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}

	// Manage the relationship between the entities
	public void addCoupon(Coupon c) {
		this.coupons.add(c);
		c.getCustomers().add(this);
	}

	// Manage the relationship between the entities
	public void removeCoupon(Customer cst) {
		for (Coupon coupon : cst.coupons) {
			coupon.getCustomers().remove(this);
		}
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}

}
