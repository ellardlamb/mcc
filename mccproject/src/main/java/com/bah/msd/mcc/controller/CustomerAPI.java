package com.bah.msd.mcc.controller;

import java.net.URI;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@Autowired
	private CustomerRepository repo;
	
	@GetMapping("/customers/all")
	public Iterable<Customer> getAll() {
		return repo.findAll();
	}
	
	@GetMapping("/customers/{name}")
	public Customer getCustomerByName(@PathVariable String name) {
		Customer customer = repo.findByNameAllIgnoringCase(name);

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
	
	@PutMapping("/customers/{name}")
	public ResponseEntity<?> putCustomer(@RequestBody Customer newCustomer, @PathVariable("name") String name) {
		if(!newCustomer.getName().equalsIgnoreCase(name)
			|| newCustomer.getEmail() == null
			|| newCustomer.getPassword() == null
			|| newCustomer.getId() == null) {
			return ResponseEntity.badRequest().build();
		}
		newCustomer = repo.save(newCustomer);
		return ResponseEntity.ok().build();
	}
	
	@Transactional
	@DeleteMapping("/customers/{name}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("name") String name) {
		repo.deleteByName(name);
		return ResponseEntity.ok().build();
	}
}