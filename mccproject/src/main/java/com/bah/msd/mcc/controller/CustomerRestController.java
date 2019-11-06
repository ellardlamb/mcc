package com.bah.msd.mcc.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.mcc.domain.Customer;
import com.bah.msd.mcc.repository.InMemoryCustomerRepository;

@RestController
@RequestMapping("/account")
public class CustomerRestController {
	private InMemoryCustomerRepository data = new InMemoryCustomerRepository();
	
	@GetMapping("/customers/all")
	public Collection<Customer> getAll() {
		return data.getCustomers();
	}
	
	@GetMapping("/customers/{name}")
	public Customer getCustomerByName(@PathVariable String name) {
		Collection<Customer> customers = data.getCustomers();

		for(Customer customer : customers) {
			if(customer.getName().contentEquals(name)) {
				return customer;
			}
		}
		return null;
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(HttpServletResponse response, @RequestBody Customer newCustomer) {
		Customer customer = new Customer(newCustomer.getName(),
				newCustomer.getEmail(), newCustomer.getPassword());
		
		data.addCustomer(customer);
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		return customer;
	}
}