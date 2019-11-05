package com.bah.msd.mcc.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bah.msd.mcc.domain.Customer;


public class InMemoryCustomerRepository {
	
	List<Customer> customers = new ArrayList<>();
	
	public void createInitData() {
		Customer cust1 = new Customer("name1", "name1@test.net", "pass1");
		Customer cust2 = new Customer("name2", "name1@test.net", "pass1");
		Customer cust3 = new Customer("name3", "name1@test.net", "pass1");
		
		customers.add(cust1);
		customers.add(cust2);
		customers.add(cust3);
		

	}
	public InMemoryCustomerRepository() {
		this.createInitData();
	}
	public Customer getCustomerByName(Integer index) {
		return customers.get(index);
	}
	public Collection<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
