package com.sri.eGameScoreAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sri.eGameScoreAPI.entity.Coach;
import com.sri.eGameScoreAPI.service.CoachService;

@RestController
@RequestMapping("/coach")
public class CoachController {
	
	// *** PERTAINS to "coach" endpoint testing
	// ***
	// *** Valid Endpoints for testing in for Coach:
	// *** http:/localhost:8080/coach/register - POST - to add a coach entry into Coach table.
	// *** http:/localhost:8080/coach/login - POST - to login as a coach.
	// *** http:/localhost:8080/coach/id - GET to find a coach by supplied id.
	// *** http:/localhost:8080/coach - GET to find all the coach records in the Coach table.
	// *** http:/localhost:8080/game/{id}/update - PUT to update a coach record in the Coach table.
	// *** http:/localhost:8080/coach/{id}/edit - to update the username of a coach.
	// *** http:/localhost:8080/coach/{id} - DELETE to delete a coach record in the Coach table
	
	@Autowired
	private CoachService service;
	
	//Routine to create a Coach record.
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody Coach coach) {
		return new ResponseEntity<Object>(service.createCoach(coach), HttpStatus.OK);
		
	}
	
	//Routine to login as a coach.  This routine can be used when it is built as a web application later.
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Coach coach) {
		try{
			return new ResponseEntity<Object>(service.login(coach), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	//Routine to get one coach from the coach table by id supplied.
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCoach(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getCoachById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//Routine to get all coaches from the coach table.
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getCoaches() {
		return new ResponseEntity<Object>(service.getCoaches(), HttpStatus.OK);
	}
	
	//Routine to update the Coach record.
	@RequestMapping(value="{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCoach(@RequestBody Coach coach, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateCoach(coach, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//Routine to update the username of Coach.
	@RequestMapping(value="{id}/edit", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCoachUserName(@RequestBody Coach coach, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateCoachUserName(coach, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//Routine to delete a Coach record.
	@RequestMapping(value="{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<Object> deleteCoach(@PathVariable Long id) {
		try {
			service.deleteCoach(id);
			return new ResponseEntity<Object>("Successfully deleted coach", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}	
	

}