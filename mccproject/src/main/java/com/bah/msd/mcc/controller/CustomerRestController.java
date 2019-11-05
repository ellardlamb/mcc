package com.bah.msd.mcc.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.mcc.domain.Customer;
import com.bah.msd.mcc.repository.InMemoryCustomerRepository;

@RestController
@RequestMapping("/accounts")
public class CustomerRestController {
	private InMemoryCustomerRepository data = new InMemoryCustomerRepository();
	
	@GetMapping("/customers")
	public Collection<Customer> getAll() {
		return data.getCustomers();
	}
	
	@GetMapping("/customers/{name}")
	public Customer getCustomerByName(@PathVariable String name) {
		Collection<Customer> customers = data.getCustomers();
//		Optional<Customer> customerInfoOptional = this.data.stream()
//				.filter(customer -> customer.getName().equals(name))
//				.findAny();
//		return customerInfoOptional.get();
		for(Customer customer : customers) {
			if(customer.getName().contentEquals(name)) {
				return customer;
			}
		}
		return null;
	}

}
