package com.bah.msd.mcc.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.bah.msd.mcc.domain.Customer;
import com.bah.msd.mcc.repository.CustomerRepository;

@RestController
@RequestMapping("/account")
public class CustomerAPI {
//	private InMemoryCustomerRepository data = new InMemoryCustomerRepository();
	@Autowired
	private CustomerRepository repo;
	
	@GetMapping("/customers/all")
	public Iterable<Customer> getAll() {
		return repo.findAll();
	}
	
	@GetMapping("/customers/{name}")
	public Customer getCustomerByName(@PathVariable String name) {
		Customer customer = repo.findByNameAllIgnoringCase(name);

//		for(Customer customer : customers) {
//			if(customer.getName().contentEquals(name)) {
//				return customer;
//			}
//		}
		return customer;
	}
	
	@PostMapping("/customers")
	public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder uri) {
		if(newCustomer.getId() != 0
			|| newCustomer.getName() == null
			|| newCustomer.getEmail() == null
			|| newCustomer.getPassword() == null) {
			return ResponseEntity.badRequest().build();					
		}
		newCustomer = repo.save(newCustomer);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
				.buildAndExpand(newCustomer.getName()).toUri();
		
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		
		return response;
	}
	
//	public Customer addCustomer(HttpServletResponse response, @RequestBody Customer newCustomer) {
//		Customer customer = new Customer(newCustomer.getName(),
//				newCustomer.getEmail(), newCustomer.getPassword());
//		
//		data.addCustomer(customer);
//		
//		response.setStatus(HttpServletResponse.SC_CREATED);
//		return customer;
//	}
	
}