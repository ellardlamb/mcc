package com.bah.msd.mcc.controller;

import java.net.URI;
import java.util.Iterator;
import java.util.Optional;

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
@RequestMapping("/customers")
public class CustomerAPI {

	@Autowired
	private CustomerRepository repo;

	@GetMapping
	public Iterator<Customer> getAllCustomers() {
		return repo.findAll().iterator();
	}

	
	@GetMapping("/byname/{name}")
	public Customer getCustomerByName(@PathVariable String name) { 
		Customer customer = repo.findByNameAllIgnoringCase(name);
		return customer;
	}
	 
	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable Long id) { 
		Optional<Customer> customer = repo.findById(id);
		return customer.get();
	}
	 

	@PostMapping("/byname/{name}")
	public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer, @PathVariable String name,
		  UriComponentsBuilder uri) {
		if (newCustomer.getName() == null
	      || !newCustomer.getName().equals(name)
	      || repo.existsByName(name)
		  || newCustomer.getEmail() == null
		  || newCustomer.getPassword() == null)
		{
		  return ResponseEntity.badRequest().build();
		}
	  
		newCustomer = repo.save(newCustomer);
  
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			  .path("/byname/{name}")
				  .buildAndExpand(newCustomer.getName()).toUri();
  
		ResponseEntity<?> response = ResponseEntity.created(location).build();
	  
		return response;
	  }
	 

	
	  @PutMapping("/byname/{name}")
	  public ResponseEntity<?> updateCustomerByName(@RequestBody Customer updateCustomer, 
			  @PathVariable("name") String name) {
		  
		  if(!updateCustomer.getName().equalsIgnoreCase(name)
			 || updateCustomer.getEmail() == null
		     || updateCustomer.getPassword() == null)
		  {
			  return ResponseEntity.badRequest().build();
		  }
		  
		  //Check it user exists in repo, if not, return 404
		  if(!repo.existsByName(name)) {
			  return ResponseEntity.notFound().build();
		  }
		  
		  updateCustomer = repo.save(updateCustomer);
		  
		  return ResponseEntity.ok().build();
	  }
	 

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomerById(@RequestBody Customer updateCustomer, @PathVariable("id") Long id) {
		if (!updateCustomer.getId().equals(id)
				|| updateCustomer.getEmail() == null
				|| updateCustomer.getPassword() == null
				|| updateCustomer.getId() == null) {
			return ResponseEntity.badRequest().build();
		}
		updateCustomer = repo.save(updateCustomer);
		return ResponseEntity.ok().build();
	}

	@Transactional
	@DeleteMapping("/{name}")
	public ResponseEntity<?> deleteCustomerByName(@PathVariable("name") String name) {
		repo.deleteByName(name);
		return ResponseEntity.ok().build();
	}
	 

//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteCustomerById(@PathVariable("id") Long id) {
//		repo.deleteById(id);
//		return ResponseEntity.ok().build();
//	}
}