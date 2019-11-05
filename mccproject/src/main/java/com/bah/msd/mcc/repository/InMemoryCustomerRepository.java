package com.bah.msd.mcc.repository;

import java.util.ArrayList;
import java.util.List;

import com.bah.msd.mcc.domain.Customer;


public class InMemoryCustomerRepository {
	
	List<Customer> customers = new ArrayList<>();
	
	public List<Customer> createInitData() {
		Customer cust1 = new Customer("name1", "name1@test.net", "pass1");
		Customer cust2 = new Customer("name2", "name1@test.net", "pass1");
		Customer cust3 = new Customer("name3", "name1@test.net", "pass1");
		
		customers.add(cust1);
		customers.add(cust2);
		customers.add(cust3);
		
		return customers;
	}
	
	public Customer getCustomer(int index) {
		return customers.get(index);
	}

}
