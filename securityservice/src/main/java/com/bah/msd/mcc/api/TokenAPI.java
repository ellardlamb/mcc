package com.bah.msd.mcc.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import com.bah.msd.mcc.util.Authenticator;
import com.bah.msd.mcc.util.JWTHelper;
import com.bah.msd.mcc.util.JWTUtil;
import com.bah.msd.mcc.valueobject.TokenRequestData;

@RestController
@RequestMapping("/token")
public class TokenAPI {
	
	RestTemplate restTemplate = new RestTemplate();
	JWTUtil jwtUtil = new JWTHelper();
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> getToken(@RequestBody TokenRequestData tokenRequestData,
			@Value("${dataApi.url}") String baseUrl) {
		
		String username = tokenRequestData.getName();
		String password = tokenRequestData.getPassword();
		
		//Building URI for adding new customer
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/customers/byname/{name}")
				.buildAndExpand(username).toUri();
		//Creating token for app access
		Token accessToken = jwtUtil.createToken("com.api.customer.r");
		
		//Creating authorization request header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken.getToken());
		
		HttpEntity<Customer> request = new HttpEntity<>(headers);
		ResponseEntity<Customer> customerEntity = 
				restTemplate.exchange(uri, HttpMethod.GET, request, Customer.class);
		Customer customer = customerEntity.getBody();
		
		if (customer != null && username != null && username.length() > 0 
				&& password != null && password.length() > 0 
				&& Authenticator.checkCredentials(username, password, customer)) {
			accessToken = jwtUtil.createToken("com.api.customer.r");
			ResponseEntity<?> response = ResponseEntity.ok(accessToken);
			return response;
		}
		// bad request
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}
}