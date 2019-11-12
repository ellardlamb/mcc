package com.bah.msd.mcc.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.mcc.domain.Customer;
import com.bah.msd.mcc.domain.Token;
import com.bah.msd.mcc.util.Authenticator;
import com.bah.msd.mcc.util.JWTHelper;
import com.bah.msd.mcc.util.JWTUtil;
import com.bah.msd.mcc.valueobject.TokenRequestData;

@RestController
@RequestMapping("/token")
public class TokenAPI {
	
	JWTUtil jwtUtil = new JWTHelper();
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> getToken(@RequestBody TokenRequestData tokenRequestData) {
		
		String username = tokenRequestData.getUsername();
		String password = tokenRequestData.getPassword();
		
		//Customer customer = repo.findByNameAllIgnoringCase(username);
		//TODO: Need to grab customer from database through REST Template
		Customer customer = new Customer(username, "bruce@a.com", password);
		
		if (customer != null && username != null && username.length() > 0 
				&& password != null && password.length() > 0 
				&& Authenticator.checkCredentials(username, password, customer)) {
			Token token = jwtUtil.createToken("secret");
			ResponseEntity<?> response = ResponseEntity.ok(token);
			return response;			
		}
		// bad request
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}
}