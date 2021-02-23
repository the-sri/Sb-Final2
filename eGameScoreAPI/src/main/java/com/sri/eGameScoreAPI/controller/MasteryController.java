package com.sri.eGameScoreAPI.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sri.eGameScoreAPI.entity.Mastery;
import com.sri.eGameScoreAPI.service.MasteryService;

@RestController
@RequestMapping("/game")
public class MasteryController {
	
	// *** PERTAINS to "Mastery" endpoint testing
	// ***
	// *** Valid Endpoints for testing in Mastery/Game:
	// *** http:/localhost:8080/game - POST - to add a game into Mastery
	// *** http:/localhost:8080/game/{id} - to GET a game from Mastery by id.
	// *** http:/localhost:8080/game/unassignedmembers - GET to find unassigned members
	// *** http:/localhost:8080/game/{id}/addumpires - POST to add umpires to a game in Mastery - use upmireId
	// *** http:/localhost:8080/game/{id}/addmembers - POST to add umpires to a game in Mastery - use memberId
	// *** http:/localhost:8080/game/{id} - DELETE to update a game by gameId.
	// ***

	@Autowired
	private MasteryService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getGame(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getGameById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Object> getGames() {
		try {
			return new ResponseEntity<Object>(service.getGames(), HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value="/unassignedmembers",method = RequestMethod.GET)
	public ResponseEntity<Object> getMembersLinkedToGame() {
		try {
			return new ResponseEntity<Object>(service.getAllMembersGameIsNull(), HttpStatus.OK);
				
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> newGame(@RequestBody Mastery getGame) {
		try {
			return new ResponseEntity<Object>(service.newGame(getGame), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value="/{id}/addumpires",method = RequestMethod.POST)
	public ResponseEntity<Object> addUmpiresToGame(@RequestBody Set<Long> umpireIds, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.submitUmpiresIntoGame(umpireIds, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value="/{id}/addmembers",method = RequestMethod.POST)
	public ResponseEntity<Object> addMembersToGame(@RequestBody Set<Long> memberIds, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.submitMembersIntoGame(memberIds, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateGame(@RequestBody Mastery getGame, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateGame(getGame, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteGame(@PathVariable Long id) {
		try {
			service.deleteGame(id);
			return new ResponseEntity<Object>("Succefully deleted Game.", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
