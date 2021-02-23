package com.sri.eGameScoreAPI.service;

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
public class ScoreService {
	
	private static final Logger logger = LogManager.getLogger(ScoreService.class);
	 
	@Autowired
	private ScoreRepository repo;
	
	@Autowired
	private MasteryRepository gameRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private TourneyRepository matchRepo;	
	
	public Score addMatchMemberInfo(Score pointScore, Long getGameId, Long matchId, Long memberId) throws Exception{
		try {
			Mastery gameId =  gameRepo.findOne(getGameId);
			Tourney assignment = matchRepo.findOne(matchId);
			Member member = memberRepo.findOne(memberId);
			
			
			if(gameId == null && assignment == null && member == null) {
				throw new Exception("Can't find assignment or member");
			}
			
			Double checkAssignmentPoints = pointScore.getMatchpoint();
			Double checkTotalAssignmentPoints = assignment.getTotalPoints();
			
			
			if(checkAssignmentPoints > checkTotalAssignmentPoints) {
				checkAssignmentPoints = assignment.getTotalPoints();
			} else if(checkAssignmentPoints < 0.00) {
				checkAssignmentPoints = 0.00;
			}
			
			pointScore.setGameid(gameId.getId());
			pointScore.setMatchid(assignment.getId());
			pointScore.setMemberid(member.getId());
			pointScore.setMatchpoint(checkAssignmentPoints);
			pointScore.setTotalPoints(assignment.getTotalPoints()); 
			member.setPoints(calculateMemberGrade(gameId.getId(), assignment.getId(), member.getId()));
			
			
			return repo.save(pointScore);
			
		} catch(Exception e) {
			logger.error("Exception occurred while tyring to add member to assignment"+ memberId, e);
			throw new Exception("Unable to add member points info");
		}
	
	}
	
	
	public Score updateMatchpoint(Score points, Long matchId) throws Exception {
		try {
			
			Score oldPoint = repo.findOne(matchId);
			Member member = memberRepo.findOne(oldPoint.getMemberid());
			
			Double checkAssignmentPoints = points.getMatchpoint();
			Double checkTotalAssignmentPoints = oldPoint.getTotalPoints();
			
			
			if(checkAssignmentPoints > checkTotalAssignmentPoints) {
				checkAssignmentPoints = checkTotalAssignmentPoints; // checkTotalAssigmentPoints
			} else if(checkAssignmentPoints < 0.00) {
				checkAssignmentPoints = 0.00;
			}
			
			oldPoint.setMatchpoint(checkAssignmentPoints);
			
			member.setPoints(calculateMemberGrade(oldPoint.getGameid(), oldPoint.getMatchid(), oldPoint.getMemberid()));	
			return repo.save(oldPoint);
							
		} catch(Exception e) {
			logger.error("Exception occurred while trying to update match points: " + matchId, e);
			throw new Exception("Unable to update match points");
		}	
		
	}
	
	private String calculateMemberGrade(Long getGameId, Long matchId, Long memberId) {
		Member member = memberRepo.findOne(memberId);
		Mastery gameId = gameRepo.findOne(getGameId);

		
		Iterable<Score> getPoints = repo.findAll();

		
		Set<Double> points = new HashSet<Double>();
		Set<Double> totalPoints = new HashSet<Double>();
		double average = 0;
		double findGrade = 0;
		double total = 0;
		String Letter = "";
		
		for(Score foundPoints : getPoints) {
			if(foundPoints.getMemberid() == member.getId() && foundPoints.getGameid() == gameId.getId()) {
				points.add(foundPoints.getMatchpoint());
				totalPoints.add(foundPoints.getTotalPoints());
				
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
