package com.bah.msd.mcc.controller;

import java.net.URI;
import java.util.Optional;

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

import com.bah.msd.mcc.domain.Registration;
import com.bah.msd.mcc.repository.RegistrationRepository;

@RestController
@RequestMapping("/account/registrations")
public class RegistrationAPI {
  
	@Autowired
	private RegistrationRepository repo;
	
	@GetMapping
	public Iterable<Registration> getAllRegistrations() {
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Registration> getRegistrationById(@PathVariable Long id) {
		Optional<Registration> registration = repo.findById(id);
		
		return registration;
	}
	
	@PostMapping
	public ResponseEntity<?> addRegistration(@RequestBody Registration newRegistration, UriComponentsBuilder uri) {
		if(newRegistration.getId() != 0
			|| newRegistration.getCustomer() == null
			|| newRegistration.getEvent() == null
			|| newRegistration.getDate() == null
			|| newRegistration.getNote() == null) {
			return ResponseEntity.badRequest().build();					
		}
		newRegistration = repo.save(newRegistration);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newRegistration.getId()).toUri();
		
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		
		return response;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRegistrationById(@RequestBody Registration updateRegistration, @PathVariable("id") Long id) {
		if(updateRegistration.getId() != 0
				|| updateRegistration.getCustomer() == null
				|| updateRegistration.getEvent() == null
				|| updateRegistration.getDate() == null
				|| updateRegistration.getNote() == null) {
			return ResponseEntity.badRequest().build();
		}
		updateRegistration = repo.save(updateRegistration);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRegistrationById(@PathVariable("id") Long id) {
		repo.deleteById(id);
		return ResponseEntity.ok().build();
	}	
}