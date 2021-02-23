package com.sri.eGameScoreAPI.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.sri.eGameScoreAPI.entity.Coach;
import com.sri.eGameScoreAPI.repository.CoachRepository;
import com.sri.eGameScoreAPI.util.AccountLevel;

@Service
public class CoachService {
private static final Logger logger = LogManager.getLogger(CoachService.class);
	
	@Autowired
	private CoachRepository repo;
	
	private UserAuthService auth = new UserAuthService();
	
	
	public Coach getCoachById(Long id) throws Exception {
		try {
			return repo.findOne(id);
			
		} catch(Exception e) {
			logger.error("Can not find Coach id: " + id, e);
			throw e;
		}
	}
	
	public Iterable<Coach> getCoaches() {
		return repo.findAll();
	}
	
	public Coach createCoach(Coach coach) {
		coach.setPassword(auth.passwordHash(coach.getPassword()));
		coach.setLevel(AccountLevel.COACH);
		return repo.save(coach);
	}
	
	public Coach login(Coach coach) throws Exception {
		Coach foundCoach = repo.findByUsername(coach.getUsername());
		String password = coach.getPassword();

		if(foundCoach != null && BCrypt.checkpw(password, foundCoach.getPassword())) {
			return foundCoach;
		} else {
			throw new Exception("Invalid username or password");
		}
	}
	
	public Coach updateCoach(Coach coach, Long id) throws Exception {
		try {
			Coach oldCoach = repo.findOne(id);
			oldCoach.setTeamName(coach.getTeamName());
			oldCoach.setFirstName(coach.getFirstName());
			oldCoach.setLastName(coach.getLastName());
			oldCoach.setAddress(coach.getAddress());
			oldCoach.setState(coach.getState());
			oldCoach.setCity(coach.getCity());
			oldCoach.setZip(coach.getZip());
			oldCoach.setPhoneNumber(coach.getPhoneNumber());
			oldCoach.setEmail(coach.getEmail());
			oldCoach.setLevel(AccountLevel.COACH);
			return repo.save(oldCoach);
		} catch(Exception e) {
			logger.error("Can't update parent id: " + id, e);
			throw new Exception("Unable to update parent information");
		}
	}
	
	public Coach updateCoachUserName(Coach coach, Long id) throws Exception {
		try {
			Coach oldParent = repo.findOne(id);
			oldParent.setUsername(coach.getUsername());
			oldParent.setPassword(auth.passwordHash(coach.getPassword()));
			return repo.save(oldParent);
		} catch(Exception e) {
			logger.error("Can't update parent id: " + id, e);
			throw new Exception("Unable to update parent username or password");
		}
	} 
	
	public void deleteCoach(Long id) throws Exception {
		try {
			repo.delete(id);
		} catch(Exception e) {
			logger.error("Can't delete Coach id: "+id,e);
			throw new Exception("Unable to delete Coach");
		}
	}

}