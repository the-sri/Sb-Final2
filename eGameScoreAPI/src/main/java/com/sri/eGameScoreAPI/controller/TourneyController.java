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

import com.sri.eGameScoreAPI.entity.Score;
import com.sri.eGameScoreAPI.entity.Tourney;
import com.sri.eGameScoreAPI.service.ScoreService;
import com.sri.eGameScoreAPI.service.TourneyService;



@RestController
@RequestMapping("umpires/{umpId}/game/{gameId}/matches")
public class TourneyController {
	
	// *** PERTAINS to "tourney" endpoint testing
	// ***
	// *** http://localhost:8080/umpires/{umpId}/game/{gameId}/matches - use POST to add match to umpire/game 
	// *** http://localhost:8080/umpires/{umpId}/game/{gameId}/matches - use GET to get all matches
	// *** http://localhost:8080/umpires/{umpId}/game/{gameId}/matches/{id} - use GET to get match by id
	// *** http://localhost:8080/umpires/{umpId}/game/{gameId}/matches/(id}/addMembers - use PUT to add Set<match> from Tourney to member
	// *** http://localhost:8080/umpires/{umpId}/game/{gameId}/matches/{matchid}/member/(memberid} - use PUT to add points to member in Score table
	// *** http://localhost:8080/umpires/{umpId}/game/{gameId}/matches/{matchid}/member/(memberid}/edit/edit/{matchId} - use PUT to update match point for member in Score table
	// *** http://localhost:8080/umpires/{umpId}/game/{gameId}/matches/{id}/update - use PUT to add update the match table items (like description)
	// *** http://localhost:8080/umpires/{umpId}/game/{gameId}/matches/{id} - use DELETE to delete a member record by memberID.
	// ***

	@Autowired
	private TourneyService service;
	
	@Autowired
	private ScoreService pointservice;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getMatchById(@PathVariable Long id) {
		try	{
			return new ResponseEntity<Object>(service.getMatchById(id), HttpStatus.OK); 
		}	catch(Exception e)	{
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getAssignments() {
		return new ResponseEntity<Object>(service.getListAssignmentsGrades(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> newAssignment(@RequestBody Tourney match, @PathVariable Long gameId) {
		try {
			return new ResponseEntity<Object>(service.newMatch(match, gameId), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	//add list of members to game assignment
	@RequestMapping(value="/{id}/addMembers", method = RequestMethod.PUT)
	public ResponseEntity<Object> addMembersToAssignments(@RequestBody Set<Long> memberIds, @PathVariable Long id,@PathVariable Long gameId) {
		try {
			 return new ResponseEntity<Object>(service.submitMembersIntoMatch(memberIds, id, gameId), HttpStatus.OK);
 
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	//add the points to the member match
	@RequestMapping(value="/{id}/member/{memberId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> addMemberPoints(@RequestBody Score points, @PathVariable Long gameId, @PathVariable Long id,@PathVariable Long memberId) {
		try {
			return new ResponseEntity<Object>(pointservice.addMatchMemberInfo(points, gameId, id, memberId), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//update match point of the member
	@RequestMapping(value="/{id}/member/{memberId}/edit/{matchId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateMatchpoint(@RequestBody Score match, @PathVariable Long matchId) {
		try {
			return new ResponseEntity<Object>(pointservice.updateMatchpoint(match,matchId), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//update match information
	@RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateMatch(@RequestBody Tourney match, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateMatch(match, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<Object> deleteMatch(@PathVariable Long id) {
		try {
			service.removeMatch(id);
			return new ResponseEntity<Object>("Successfully deleted the match", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
