
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

import com.bah.msd.mcc.domain.Event;
import com.bah.msd.mcc.repository.EventRepository;

@RestController
@RequestMapping("/account/events")
public class EventAPI {
  
	@Autowired
	private EventRepository repo;
	
	@GetMapping
	public Iterable<Event> getAllEvents() {
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Event> getEventById(@PathVariable Long id) {
		Optional<Event> event = repo.findById(id);
		
		return event;
	}
	
	@PostMapping
	public ResponseEntity<?> addEvent(@RequestBody Event newEvent, UriComponentsBuilder uri) {
		if(newEvent.getId() != 0
			|| newEvent.getCode() == null
			|| newEvent.getDescription() == null
			|| newEvent.getTitle() == null) {
			return ResponseEntity.badRequest().build();					
		}
		newEvent = repo.save(newEvent);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newEvent.getId()).toUri();
		
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		
		return response;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEventById(@RequestBody Event updateEvent, @PathVariable("id") Long id) {
		if(updateEvent.getId() != id
				|| updateEvent.getCode() == null
				|| updateEvent.getDescription() == null
				|| updateEvent.getTitle() == null) {
			return ResponseEntity.badRequest().build();
		}
		updateEvent = repo.save(updateEvent);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEventById(@PathVariable("id") Long id) {
		repo.deleteById(id);
		return ResponseEntity.ok().build();
	}	
}