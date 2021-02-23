package com.sri.eGameScoreAPI.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.eGameScoreAPI.entity.Mastery;
import com.sri.eGameScoreAPI.entity.Member;
import com.sri.eGameScoreAPI.entity.Referee;
import com.sri.eGameScoreAPI.repository.MasteryRepository;
import com.sri.eGameScoreAPI.repository.MemberRepository;
import com.sri.eGameScoreAPI.repository.RefereeRepository;

@Service
public class MasteryService {
	
	private static final Logger logger = LogManager.getLogger(MasteryService.class);
	
	@Autowired
	private MasteryRepository repo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private RefereeRepository umpRepo;
	
	public Mastery getGameById(Long id) throws Exception {
		try {
			return repo.findOne(id);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to retrive game: " + id, e);
			throw new Exception("Unable to find game");
		}
	}
	
	public Iterable<Mastery> getGames() {
		return repo.findAll();
	}
		
	public Mastery newGame(Mastery getGame) throws Exception {
		getGame.setDateGameCreated(LocalDate.now());
		return repo.save(getGame);
	}

	public Set<String> getAllMembersGameIsNull() {
		Iterable<Member> members = memberRepo.findAll(); 
		Set<String> list = new HashSet<String>();
		for(Member member : members) {
			if(member.getGame().isEmpty()) {
				list.add("MemberId: " + member.getId() + ", Member Name: " + member.getFirstName() + " " +member.getLastName() + ", Member Grade Level: " + member.getGradeLevel());
			}
		}
		return list;
	}
	
	public Mastery submitMembersIntoGame(Set<Long> memberId, Long gameId) throws Exception {
		Mastery getGameId = repo.findOne(gameId);
		try {
			if(getGameId == null) {
				throw new Exception("Can't find Game");
			}
			Mastery gameroom = initializeNewGamesMembers(memberId,getGameId.getId());
			return repo.save(gameroom);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to add member for the game-masteryService", e);
			throw new Exception("Unable to add members for the game!");
		}
	}
	
	public Mastery submitUmpiresIntoGame(Set<Long> umpireIds, Long gameId) throws Exception {
		Mastery getGameId = repo.findOne(gameId);
		try {
			if(getGameId == null) {
				throw new Exception("Can't find the game!");
			}
			Mastery gameroom = initializeNewGameUmpires(umpireIds,getGameId.getId());
			return repo.save(gameroom);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to add member for the game-masteryService.", e);
			throw new Exception("Unable to add umpire for the game.");
		}
	}

	public Mastery updateGame(Mastery getGame, Long id) throws Exception {
		try {
			Mastery oldGame = repo.findOne(id);
			oldGame.setGameTitle(getGame.getGameTitle());
			oldGame.setGameDetail(getGame.getGameDetail());
			return repo.save(oldGame);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to update Game: " + id, e);
			throw new Exception("Unable to update game");
		}
	}
	
	public void deleteGame(Long id) throws Exception{
		try {
			 repo.delete(id);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to delete Game: " + id, e);
			throw new Exception("Unable to delete game");
		}
	}
	
	private Mastery initializeNewGamesMembers(Set<Long> memberIds, Long gameId) {
		Mastery gameroom = repo.findOne(gameId);
		gameroom.setMembers(convertMembersToSet(gameId,gameroom.getGradeLevel(), memberRepo.findAll(memberIds)));
		addGamesToMembers(gameroom, gameroom.getGradeLevel());
		return gameroom;
	}
	
	private void addGamesToMembers(Mastery gameroom, String gradeLevel) {
		Set<Member> members = gameroom.getMembers();
		for(Member newmember : members) {
			if(gameroom.getGradeLevel().equals(newmember.getGradeLevel())) {
					newmember.getGame().add(gameroom);			
			} 
		}
	}
	
	private Set<Member> convertMembersToSet(Long gameId, String gradeLevel, Iterable<Member> iterable) {
		Mastery gameroom = repo.findOne(gameId);
		Set<Member> set = new HashSet<Member>();
		int count = 0;
		for(Member member : iterable) {
			if(gameroom.getGradeLevel().equals(member.getGradeLevel())) {
				if(count < gameroom.getMaxNumberofMembers()) {
					count ++;
						set.add(member);
				}
			} 
		}
		return set;
	}
	
	private Mastery initializeNewGameUmpires(Set<Long> umpireId, Long gameId) {
		Mastery gameroom = repo.findOne(gameId);
		gameroom.setUmpireName(convertEmployeesToSet(umpRepo.findAll(umpireId)));
		addClassesToEmployee(gameroom);
		return gameroom;
	}
	
	private void addClassesToEmployee(Mastery gameroom) {
		Set<Referee> employees = gameroom.getUmpireName();
		for(Referee newEmployee : employees) {
			newEmployee.getListGameroom().add(gameroom);			
		}
	}
	
	private Set<Referee> convertEmployeesToSet(Iterable<Referee> iterable) {
		Set<Referee> set = new HashSet<Referee>();
		for(Referee umpire : iterable) {
				set.add(umpire);
		}
		return set;
	}







	
}
