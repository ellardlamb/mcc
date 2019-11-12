package com.bah.msd.mcc.util;

import com.bah.msd.mcc.domain.Customer;

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
