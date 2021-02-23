package com.sri.eGameScoreAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sri.eGameScoreAPI.entity.Member;
import com.sri.eGameScoreAPI.service.MemberService;

@RestController
@RequestMapping("/coach/{coachId}/member/")
public class MemberController {
	

	@Autowired
	private MemberService service;
	
	// *** PERTAINS to "member" endpoint testing
	// ***
	// *** http://localhost:8080/coach/{coachId}/member/{id} - use GET for specific memberID of the coach 
	// *** http://localhost:8080/coach - use GET to get all coach records
	// *** http://localhost:8080/coach/{id}/member - use POST to add a new team member and assign to coachfor coach by coachID
	// *** http://localhost:8080/coach/{coachId}/member/{id}/update - use PUT to update a member record by memberID
	// *** http://localhost:8080/coach/{coachId}/member/{id} - use DELETE to delete a member record by memberID.
	// ***
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getMember(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getMemberById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Object> getMembers() {
		try {
			return new ResponseEntity<Object>(service.getMembers(), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
			
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> newMember(@RequestBody Member member,@PathVariable Long coachId) {
		try {
			return new ResponseEntity<Object>(service.newMember(member, coachId), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateMember(@RequestBody Member member, @PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.updateMember(member, id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteMember(@PathVariable Long id) {
		try {
			service.deleteMember(id);
			return new ResponseEntity<Object>("Succefully deleted member.", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}


}
