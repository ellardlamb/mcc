package com.bah.msd.mcc.security;

import org.springframework.stereotype.Component;

import com.bah.msd.mcc.domain.Customer;

@Component
public class Authenticator {
	
	public static boolean checkUser(String username, String usernameCheck) {
		
		if( (username != null && username.length() > 0) &&
			( username.equalsIgnoreCase(usernameCheck)) ) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean checkCredentials(String username, String password, Customer customer) {
		
		if(checkUser(username, customer.getName()) && password.equalsIgnoreCase(customer.getPassword())) {
				return true;
		}else {
			return false;
		}
	}
}
