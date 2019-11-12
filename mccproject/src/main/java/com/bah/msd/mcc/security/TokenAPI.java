package com.bah.msd.mcc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.mcc.domain.Customer;
import com.bah.msd.mcc.repository.CustomerRepository;

@RestController
@RequestMapping("/token")
public class TokenAPI {
	
	@Autowired
	private CustomerRepository repo;
	
	JWTUtil jwtUtil = new JWTHelper();
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> getToken(@RequestBody TokenRequestData tokenRequestData) {
		
		String username = tokenRequestData.getUsername();
		String password = tokenRequestData.getPassword();
		
		Customer customer = repo.findByNameAllIgnoringCase(username);
		
		if (customer != null && username != null && username.length() > 0 
				&& password != null && password.length() > 0 
				&& Authenticator.checkCredentials(username, password, customer)) {
			//String secret = username + password;
			Token token = jwtUtil.createToken("secret");
			ResponseEntity<?> response = ResponseEntity.ok(token);
			return response;			
		}
		// bad request
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}
	
	
}