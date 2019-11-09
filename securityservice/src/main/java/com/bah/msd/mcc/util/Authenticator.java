package com.bah.msd.mcc.util;

public class Authenticator {
	
	public static boolean checkUser(String username) {
		if( (username != null && username.length() > 0) &&
			( username.equalsIgnoreCase("Bruce")) ) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean checkPassword(String username, String password) {
		if(checkUser(username)) {
			if(username.equalsIgnoreCase("Bruce") && password.equals("changeit")) {
				return true;
			}		
		}else {
			return false;
		}
		return false;
	}
	
}
