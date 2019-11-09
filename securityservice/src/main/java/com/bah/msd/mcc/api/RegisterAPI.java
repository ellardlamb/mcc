package com.bah.msd.mcc.api;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bah.msd.mcc.domain.Customer;
import com.bah.msd.mcc.valueobject.RegisterRequestData;

@RestController
@RequestMapping("/register")
public class RegisterAPI {
	
	RestTemplate restTemplate = new RestTemplate();

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
			
//			URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/byname/{name}")
//					.buildAndExpand(username).toUri();
			String url = baseUrl + "/byname/" + username;
			Customer customer = new Customer(username, email, password);
			//restTemplate.postForObject(uri, customer, Customer.class);
			HttpEntity<Customer> request = new HttpEntity<>(customer);
			restTemplate.postForObject(url, request, Customer.class);
			ResponseEntity<?> response = ResponseEntity.ok().build();
			return response;		
		}
		
		//if validation fails, it is a bad request
		return ResponseEntity.badRequest().build();	
	}
}
