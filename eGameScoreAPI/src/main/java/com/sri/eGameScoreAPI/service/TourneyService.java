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
import com.sri.eGameScoreAPI.entity.Score;
import com.sri.eGameScoreAPI.entity.Tourney;
import com.sri.eGameScoreAPI.repository.MasteryRepository;
import com.sri.eGameScoreAPI.repository.MemberRepository;
import com.sri.eGameScoreAPI.repository.ScoreRepository;
import com.sri.eGameScoreAPI.repository.TourneyRepository;

@Service
public class TourneyService {
	
	private static final Logger logger = LogManager.getLogger(TourneyService.class);
	
	@Autowired
	private TourneyRepository repo;
	
	@Autowired
	private MasteryRepository gameRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private ScoreRepository gradeRepo;
	
	public Tourney getMatchById(Long id) {
		return repo.findOne(id);
	}
	
	public Iterable<Tourney> getListAssignmentsGrades() {
		return repo.findAll();
	}
	
	
	public Tourney newMatch(Tourney match, Long getGameId) throws Exception {
		try {
			
			Mastery findGame = gameRepo.findOne(getGameId);
			
			match.setGameroom(findGame);
			match.setDate(LocalDate.now());
			return repo.save(match);
			
		} catch(Exception e) {
			logger.error("Exception occurred while trying to create new match for game: " + getGameId, e);
			throw e;
		}
	}
	
	public Tourney submitMembersIntoMatch(Set<Long> memberId, Long matchId, Long gameId) throws Exception {
		Tourney getMatchId = repo.findOne(matchId);
		Mastery getGame = gameRepo.findOne(gameId);
		try {
			if(getMatchId == null) {
				throw new Exception("Can't find assignment");
			}
			Tourney assign = initializeNewMatchesToMembers(memberId,getMatchId.getId(), getGame.getId());
			return repo.save(assign);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to add member", e);
			throw new Exception("Unable to add members into assignment ");
		}
	}
	
	public Tourney updateMatch(Tourney match, Long id) throws Exception {
		try {
			
			Tourney Oldmatch = repo.findOne(id);
			Oldmatch.setTotalPoints(match.getTotalPoints());
			Oldmatch.setDescription(match.getDescription());
			return repo.save(Oldmatch);
			
		} catch(Exception e) {
			logger.error("Exception occurred while trying to create new assignment for game: " + id, e);
			throw e;
		}
	}
	
	
	public void removeMatch(Long id) throws Exception {
		try {
			repo.delete(id);
			
		} catch(Exception e) {
			logger.error("Exception occured while trying to delete match: " + id, e);
			throw new Exception("Unable to delete assignment.");
		}
	}
	
	private Tourney initializeNewMatchesToMembers(Set<Long> memberIds, Long matchId, Long gameId) {
		Tourney match = repo.findOne(matchId);
		Mastery gameroom = gameRepo.findOne(gameId);
		for(Long memberId : memberIds) {
			Member member = memberRepo.findOne(memberId);
			member.setPoints(caculateMemberGrade(gameroom.getId(),match.getId(),memberId));
		}

		match.setMembers(convertMembersToSet(match.getId(),gameroom.getId(),memberRepo.findAll(memberIds)));
		addMatchGradesToMembers(match);
			
		return match;
	}
	
	private void addMatchGradesToMembers(Tourney assign) {
		Set<Member> members = assign.getMembers();
		for(Member newmember : members) {
			if(assign.getGameroom() == newmember.getGame()) {
					newmember.getMatch().add(assign);			
			} 
		}
	}
	
	private Set<Member> convertMembersToSet(Long matchId, Long gameId, Iterable<Member> iterable) {
		Mastery gameroom = gameRepo.findOne(gameId);
		Tourney assign = repo.findOne(matchId);
		Set<Member> set = new HashSet<Member>();
		for(Member member : iterable) {
			if(gameroom.getId() == assign.getGameroom().getId()) {
				set.add(member);				
			} 
		}
		return set;
	}
	

private String caculateMemberGrade(Long getGameId, Long matchId, Long memberId) {
	Member member = memberRepo.findOne(memberId);
	Mastery gameId = gameRepo.findOne(getGameId);
	Tourney match = repo.findOne(matchId);
	Iterable<Score> getPoints = gradeRepo.findAll();
	Set<Double> points = new HashSet<Double>();
	Set<Double> totalPoints = new HashSet<Double>();
	double average = 0;
	double findGrade = 0;
	double total = 0;
	String Letter = "";

	for(Score findgrade : getPoints) {
		if(findgrade.getMatchid() == match.getId() && findgrade.getMemberid() == member.getId() && findgrade.getGameid() == gameId.getId()) {
			points.add(findgrade.getMatchpoint());
			totalPoints.add(match.getTotalPoints());
			}
		}
	
		for(Double num : points) {
			findGrade += num;
		}
	
		for(Double pointTotal : totalPoints) {
			total += pointTotal;
		}
	
		average = (findGrade/total)*100;
	
		if(average >= 90) {
			Letter = "SuperStar";
		} else if(average >= 80) {
			Letter = "Star";
		} else if(average >= 70) {
			Letter = "Proficient";
		} else if(average >= 60 ) {
			Letter = "Beginner";
		} else {
			Letter = "NeedsPractice";
		}
		return Letter;
	} 
}
