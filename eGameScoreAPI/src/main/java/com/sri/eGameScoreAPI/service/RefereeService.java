package com.sri.eGameScoreAPI.service;

import org.springframework.stereotype.Service;

import com.sri.eGameScoreAPI.entity.Referee;
import com.sri.eGameScoreAPI.repository.RefereeRepository;
import com.sri.eGameScoreAPI.util.AccountLevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class RefereeService {

private static final Logger logger = LogManager.getLogger(RefereeService.class);
	
	@Autowired
	private RefereeRepository repo;
	
	private UserAuthService auth = new UserAuthService();	
	
	public Referee getUmpireById(Long id) throws Exception {
		try {
			return repo.findOne(id);
			
		} catch(Exception e) {
			logger.error("Can't find employee id: " + id, e);
			throw e;
		}
	}
	
	public Iterable<Referee> getUmpires() {
		return repo.findAll();
	}
	
	public Iterable<Referee> getListUmpires() {
		return repo.findAll();
	}
	
	public Referee createUmpire(Referee umpire) {
		umpire.setPassword(auth.passwordHash(umpire.getPassword()));
		if(umpire.getAccountLevel() == null) {
			umpire.setAccountLevel(AccountLevel.UMPIRE);
		} 
		return repo.save(umpire);
	}
	
	public Referee login(Referee umpire) throws Exception {
		Referee foundUmp = repo.findByUsername(umpire.getUsername());
		
		String password = umpire.getPassword();
		if(foundUmp != null && BCrypt.checkpw(password, foundUmp.getPassword())) {
			return foundUmp;
		} else {
			throw new Exception("Invalid username or password");
		}
	}
	
	public Referee updateUmpire(Referee umpire, Long id) throws Exception {
		try {
			Referee oldEmp = repo.findOne(id);
			oldEmp.setFirstName(umpire.getFirstName());
			oldEmp.setLastName(umpire.getLastName());
			oldEmp.setTitle(umpire.getTitle());
			oldEmp.setAddress(umpire.getAddress());
			oldEmp.setCity(umpire.getCity());
			oldEmp.setState(umpire.getState());
			oldEmp.setZip(umpire.getZip());
			oldEmp.setEmail(umpire.getEmail());
			if(umpire.getAccountLevel() == null) {
				oldEmp.setAccountLevel(AccountLevel.UMPIRE);
			} else {
				oldEmp.setAccountLevel(umpire.getAccountLevel());
			}
			
			return repo.save(oldEmp);
		} catch(Exception e) {
			logger.error("Can't update Umpire id: " + id, e);
			throw new Exception("Unable to update umpire information");
		}
	}
	
	public Referee updateUmpireUserName(Referee umpire, Long id) throws Exception {
		try {
			Referee oldUmp = repo.findOne(id);
			oldUmp.setUsername(umpire.getUsername());
			oldUmp.setPassword(auth.passwordHash(umpire.getPassword()));
			return repo.save(oldUmp);
		} catch(Exception e) {
			logger.error("Can not update Umpire id: " + id, e);
			throw new Exception("Unable to update umpire username or password");
		}
	} 
	
	public void deleteUmpire(Long id) throws Exception {
		try {
			repo.delete(id);
		} catch(Exception e) {
			logger.error("Can't delete umpire id: "+id,e);
			throw new Exception("Unable to delete umpire");
		}
	}
}
