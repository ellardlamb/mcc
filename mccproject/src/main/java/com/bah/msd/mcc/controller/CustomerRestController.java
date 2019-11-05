package com.bah.msd.mcc.controller;

import java.util.Collection;
import java.util.List;

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
		List<Customer> returnData = data.createInitData();
		return returnData;
	}
	
	@GetMapping("/customers/{name}")
	public Customer getCustomerByName(@PathVariable String name) {
		return data.getCustomer(0);
	}

}
