package com.example.c_p.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.c_p.beans.Company;
import com.example.c_p.beans.Coupon;

/**
 * CompanyRepository is a data access logic which defines the data access a
 * Company type should have upon login
 * 
 * @function findByEmailAndPassword allows the user to search for a company by
 *           email and password identifiers, @returns the company if found
 * @function existsByNameOrEmail allows the user to check if a company exists by
 *           name OR by email, @returns T/F
 * @function findById findById allows the user to request a company by
 *           ID, @returns the company if found
 * @function existsById allows the user to check if a company exists by a
 *           certain ID, @returns T/F
 * @function findCompanyByEmailAndId allows the user to find a company by Email
 *           and ID, and @returns the company if found
 * @function findCompanyCoupons allows the user to input an ID and find if and
 *           which coupons correlate to it, @returns a List of Coupon type based
 *           on outcome
 * @function findByName allows the user to find a company by name
 *           alone, @returns a Company object based on results
 *
 */

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	public Company findByEmailAndPassword(String email, String password);

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN 'true' ELSE 'false' END FROM Company c WHERE c.name = ?1 Or c.email = ?2")
	public Boolean existsByNameOrEmail(String name, String email);

	public Company findById(int id);

	public Boolean existsById(Company company);

	@Query("select c from Company c where c.email = ?1 and c.id <> ?2")
	public Company findCompanyByEmailAndId(String email, int id);

	public Company findByName(String name);

	public List<Company> findByIdAndName(int id, String name);

	public String findByEmail(String email);

	public String findByPassword(String password);
}
