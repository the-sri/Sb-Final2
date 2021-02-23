package com.sri.eGameScoreAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sri.eGameScoreAPI.entity.Referee;
import com.sri.eGameScoreAPI.service.RefereeService;

@RestController
@RequestMapping("/referee")
public class RefereeController {
	
	// *** PERTAINS to "referee" endpoint testing
	// ***
	// *** Valid Endpoints for testing in for Referee:
	// *** http:/localhost:8080/referee/register - POST - to add a referee entry into referee table.
	// *** http:/localhost:8080/referee/login - POST - to login as a referee.
	// *** http:/localhost:8080/referee/id - GET to find a referee by supplied id.
	// *** http:/localhost:8080/referee - GET to find all the referee records in the referee table.
	// *** http:/localhost:8080/game/{id}/update - PUT to update a referee record in the referee table.
	// *** http:/localhost:8080/coach/{id}/edit - PUT to update the username of a referee.
	// *** http:/localhost:8080/coach/{id} - DELETE to delete a referee record in the referee table

	@Autowired
	private RefereeService service;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody Referee umpire)	{
		return new ResponseEntity<Object>(service.createUmpire(umpire), HttpStatus.OK);
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Referee umpire)	{
		try	{
			return new ResponseEntity<Object>(service.login(umpire), HttpStatus.OK);
		}
		catch(Exception e)	{
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);			
		}
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUmpire(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getUmpireById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getUmpires() {
		return new ResponseEntity<Object>(service.getUmpires(), HttpStatus.OK);
	}
	
	@RequestMapping(value="{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateUmpire(@RequestBody Referee umpire, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateUmpire(umpire, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="{id}/edit", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateUmpireUserName(@RequestBody Referee umpire, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateUmpireUserName(umpire, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<Object> deleteUmpire(@PathVariable Long id) {
		try {
			service.deleteUmpire(id);
			return new ResponseEntity<Object>("Successfully deleted umpire.", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
