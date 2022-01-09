package com.example.c_p.maneger;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.c_p.exception.CustomException;
import com.example.c_p.service.AdminService;
import com.example.c_p.service.ClientService;
import com.example.c_p.service.CompanyService;
import com.example.c_p.service.CustomerService;

/**
 * LogInManeger is a method which allows the users to log in and use the
 * services based on which privilege they have after inputting Email, password ,
 * and choosing which client type they identify as, the method checks for
 * validity if the validation is successful, the method returns
 * 
 * @param clientService defines which level of access the client should have,
 *                      based on their input.
 * @return sends back the type of clientService the client has access to
 */
@Service
public class LogInManeger {
	private final ApplicationContext ctx;
	private final AdminService adminService;
	private final CompanyService companyService;
	private final CustomerService customerService;
	public LogInManeger(ApplicationContext ctx, AdminService adminService, CompanyService companyService, CustomerService customerService) {
		this.ctx = ctx;
		this.adminService = adminService;
		this.companyService = companyService;
		this.customerService = customerService;
	}

	public ClientService login(String email, String password, ClientType clientType) throws CustomException {
		ClientService clientService = null;
		switch (clientType) {
		case ADMINISTRATOR:
			if(adminService.login(email, password)) {
				clientService = (ClientService) adminService;	
			}
			break;
		case COMPANY:
			if(companyService.login(email, password)) {
				clientService = (ClientService) companyService;
			}
			break;
		case CUSTOMER:		
			if(customerService.login(email, password)) {
				clientService = (ClientService) customerService;
			}
			break;
		}
		if (clientService==null) {
			throw new CustomException("Unsuccessful Login,try again!");
		}
		return clientService;
	}
}
