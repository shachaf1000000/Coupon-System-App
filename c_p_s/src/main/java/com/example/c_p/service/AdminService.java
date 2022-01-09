package com.example.c_p.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.c_p.beans.Company;
import com.example.c_p.beans.Customer;
import com.example.c_p.exception.CustomException;

@Service
@Transactional
public class AdminService extends ClientService {
	private final String EMAIL = "Admin@gmail.com";
	private final String PASSWORD = "Admin1234";

	public AdminService() {
		super();

	}

	@Override
	public boolean login(String email, String password) throws CustomException {
		if (email != null && password != null) {

			if (email.equalsIgnoreCase(EMAIL) && password.equals(PASSWORD)) {
				System.out.println("Welcome Admin!");
				return true;
			}
		}
		return false;
	}

	public void addCompany(Company company) throws CustomException {
		if (company != null) {
			if (!companiesRepo.existsByNameOrEmail(company.getName(), company.getEmail())) {
				companiesRepo.save(company);
				System.out.println("The"+ company.getName()+" company was added successfully");
			} else {
				throw new CustomException("Company already exists");
			}
		} else {
			throw new CustomException("Cannot add empty company");
		}
	}

	public void updateCompany(Company company) throws CustomException {
		List<Company> check = companiesRepo.findByIdAndName(company.getId(), company.getName());
		if (check.isEmpty()) {
			throw new CustomException("The " + company.getName() + " doesn't exist in the system " + "And The "
					+ company.getId() + " doesn't exist in the system ");
		}
		companiesRepo.save(company);
		System.out.println("Company " + company.getName() + " has been updated sucessfully");

	}

	public void deleteCompany(int companyId) throws CustomException {
		if (companyId > 0) {
			Company c = companiesRepo.findById(companyId);
			if (c == null) {
				throw new CustomException("The company does not exist in the system");
			} else {
				companiesRepo.delete(c);
				System.out.println("Company " + c.getName() + " was sucessfully deleted.");
			}
		} else {
			throw new CustomException("can't delete company with invalid Id");
		}
	}

	public ArrayList<Company> getAllCompanies() throws CustomException {
		if (companiesRepo.findAll().isEmpty()) {
			throw new CustomException("There are no companies in the system ");
		}
		System.out.println("Here are all the companies: " + companiesRepo.findAll().toString());
		return (ArrayList<Company>) companiesRepo.findAll();
	}

	public Company getOneCompany(int companyId) throws CustomException {
		if (companyId > 0) {
			Company c = companiesRepo.findById(companyId);
			if (c == null) {
				throw new CustomException("No company found with this specific Id");
			}
			System.out.println("The company you've requested is: " + c.toString());
			return c;
		} else {
			throw new CustomException("Cannot show company with invalid id ");
		}
	}

	public void addCustomer(Customer customer) throws CustomException {
		if (customer != null) {
			if (!customersRepo.existsByEmail(customer.getEmail())) {
				customersRepo.save(customer);
				System.out.println("Customer " + customer.getFirstName() + " has been added successfully!");
			} else {
				throw new CustomException("Cannot add this specific email - This email already exists in the system");
			}
		} else {
			throw new CustomException("Cannot add empty customer");
		}
	}

	public void updateCustomer(Customer customer) throws CustomException {
		Customer customer1 = customersRepo.findById(customer.getId());
		if (customer1 == null) {
			throw new CustomException(
					customer.getFirstName() + " " + customer.getLastName() + " doesn't exist in the system");
		}
		customersRepo.save(customer);
		System.out.println(
				"Customer " + customer.getFirstName() + " " + customer.getLastName() + " has been updated sucessfully");

	}

	public void deleteCustomer(int customerId) throws CustomException {
		if (customerId > 0) {
			Customer c = customersRepo.findById(customerId);
			if (c == null) {
				throw new CustomException("No customer found with this specific Id");
			} else {
				c.removeCoupon(c);
				customersRepo.delete(c);
				System.out.println("Customer " + c.getFirstName() + " was sucessfully deleted.");
			}
		} else {
			throw new CustomException("cannot delete customer with invalid Id");
		}
	}

	public ArrayList<Customer> getAllCustomers() throws CustomException {
		if ((customersRepo.findAll().isEmpty())) {
			throw new CustomException("There are no customers in the system");
		}
		System.out.println("Here are all the customers: " + customersRepo.findAll().toString());
		return (ArrayList<Customer>) customersRepo.findAll();
	}

	public Customer getOneCustomer(int customerId) throws CustomException {
		if (customerId > 0) {
			Customer c = customersRepo.findById(customerId);
			if (c == null) {
				throw new CustomException("No customer found with this specific Id");
			}
			System.out.println("The customer you requested is: " + c.toString());
			return c;
		} else {
			throw new CustomException("Cannot show customer with invalid Id");
		}
	}

}
