package com.bah.msd.mcc.controller;

import java.net.URI;
import java.util.Iterator;
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
@RequestMapping("/registrations")
public class RegistrationAPI {
	
	@Autowired
	RegistrationRepository repo;
	
	@GetMapping
	public Iterator<Registration> getAllRegistrations() {
		return repo.findAll().iterator();
	}
	
	@GetMapping("/{id}")
	public Optional<Registration> getRegistrationById(@PathVariable Long id) {
		Optional<Registration> Registration = repo.findById(id);
		
		return Registration;
	}
	
	@PostMapping
	public ResponseEntity<?> addRegistration(@RequestBody Registration newRegistration, UriComponentsBuilder uri) {
		if(    newRegistration.getCustomerId() == null
			|| newRegistration.getDate() == null
			|| newRegistration.getEventId() == null
			|| newRegistration.getNote() == null
			|| newRegistration.getNote().isEmpty()) {
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
		if( updateRegistration.getId() == null
				|| !updateRegistration.getId().equals(id)
				|| updateRegistration.getDate() == null
				|| updateRegistration.getCustomerId() == null
				|| updateRegistration.getEventId() == null
				|| updateRegistration.getNote() == null
				|| updateRegistration.getNote().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
		//Check it Registration exists in repo, if not, return 404
		//NOTE: Temporarily commenting out below check because front-end code
		//uses PUT request to create new resource...not sure why...
		//need to clarify this with instructor
//		if(!repo.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
		
		updateRegistration = repo.save(updateRegistration);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRegistrationById(@PathVariable("id") Long id) {
		repo.deleteById(id);
		return ResponseEntity.ok().build();
	}	

}
