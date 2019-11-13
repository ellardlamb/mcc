package com.bah.msd.mcc.api;



import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bah.msd.mcc.domain.Customer;
import com.bah.msd.mcc.domain.Token;
import com.bah.msd.mcc.util.JWTHelper;
import com.bah.msd.mcc.util.JWTUtil;
import com.bah.msd.mcc.valueobject.RegisterRequestData;

@RestController
@RequestMapping("/register")
public class RegisterAPI {
	
	RestTemplate restTemplate = new RestTemplate();
	JWTUtil jwtUtil = new JWTHelper();

	@PostMapping
	public ResponseEntity<?> registerCustomer(@RequestBody RegisterRequestData registerRequestData,
			@Value("${dataApi.url}") String baseUrl) {
		
		String username = registerRequestData.getName();
		String password = registerRequestData.getPassword();
		String email = registerRequestData.getEmail();
		
		//if valid inputs, create new customer and add to repository
		if (username != null && username.length() > 0 
				&& password != null && password.length() > 0
				&& email != null && email.length() > 0) {
			
			
			//Building URI for adding new customer
			URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/customers/byname/{name}")
					.buildAndExpand(username).toUri();
			//Creating token for app access
			Token accessToken = jwtUtil.createToken("com.api.customer.r");
			
			//Creating authorization request header
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + accessToken.getToken());
			
			Customer customer = new Customer(username, email, password);
			HttpEntity<Customer> request = new HttpEntity<>(customer, headers);
			restTemplate.postForObject(uri, request, Customer.class);
			ResponseEntity<?> response = ResponseEntity.ok().build();
			return response;		
		}
		
		//if validation fails, it is a bad request
		return ResponseEntity.badRequest().build();	
	}
}
