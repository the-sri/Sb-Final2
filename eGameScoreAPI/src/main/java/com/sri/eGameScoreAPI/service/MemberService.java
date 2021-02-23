package com.sri.eGameScoreAPI.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.eGameScoreAPI.entity.Coach;
import com.sri.eGameScoreAPI.entity.Member;
import com.sri.eGameScoreAPI.entity.Tourney;
import com.sri.eGameScoreAPI.repository.CoachRepository;
import com.sri.eGameScoreAPI.repository.MemberRepository;

@Service
public class MemberService {
	
	private static final Logger logger = LogManager.getLogger(MemberService.class);
	
	@Autowired
	private MemberRepository repo;

	@Autowired
	private CoachRepository coachRepo;

	public Member getMemberById(Long id) throws Exception {
		try {
			return repo.findOne(id);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to retrieve member: " + id, e);
			throw new Exception("Unable to find member by id.");
		}
	}
		
	public Iterable<Member> getMembers() {
		return repo.findAll();
	}
		
	public Member newMember(Member member, Long parentId) throws Exception {
			try {
				Coach coach = coachRepo.findOne(parentId); 
				if(coach == null) {
					throw new Exception("Cannot find coach - memberService");
				}
				int age = calculateAge(member.getBirthDate());
				member.setAge(age);
				member.setCoach(coach);
				return repo.save(member);
			} catch(Exception e) {
				logger.error("Exception occurred while tyring to add member to coach", e);
				throw new Exception("Unable to add member ");
			}
	}
		
	public Member updateMember(Member member, Long id) throws Exception {
		try {
			Member oldMember = repo.findOne(id);
			oldMember.setFirstName(member.getFirstName());
			oldMember.setLastName(member.getLastName());
			oldMember.setBirthDate(member.getBirthDate());
			oldMember.setAge(calculateAge(member.getBirthDate()));
			oldMember.setGradeLevel(member.getGradeLevel());
			oldMember.setPoints(member.getPoints());
			
			return repo.save(oldMember);
			
		} catch(Exception e) {
			logger.error("Exception occurred while trying to update member: " + id, e);
			throw new Exception("Unable to update member");
		}
			
	}

	public void deleteMember(Long id) throws Exception{
		try {
			 repo.delete(id);
		} catch(Exception e) {
			logger.error("Exception occurred while trying to delete member: " + id, e);
			throw new Exception("Unable to delete member");
		}
		
	}

	public Member addingNewMembers(Set<Long> assignmentId, Coach coach) {
		Member member = new Member();
		member.setCoach(member.getCoach());
		member.setFirstName(member.getFirstName());
		member.setLastName(member.getLastName());
		member.setBirthDate(member.getBirthDate());
		member.setAge(calculateAge(member.getBirthDate()));
		member.setGradeLevel(member.getGradeLevel());
		member.setPoints(calculateMemberGrade(member.getMatch(),member.getId()));
		
		addMembersToAssignmentsGrade(member);
		addMembersToParent(member);
		return member;
	}

	public void addMembersToAssignmentsGrade(Member member) {
		Set<Tourney> match = member.getMatch();
		for(Tourney assignment :  match) {
			assignment.getMembers().add(member);
		}
	}

	public void addMembersToParent(Member member) {
		Long parentId = member.getCoach().getId();
		Coach coach = coachRepo.findOne(parentId);
			coach.getMembers().add(member);
		}

	private String calculateMemberGrade(Set<Tourney> getGrades, Long memberId) {
		Member member = new Member();
		Set<Double> grades = new HashSet<Double>();
		int average = 0;
		String Letter = "";
		
		if(memberId.equals(member.getId())){
			for(Tourney findgrade : getGrades) {
			 grades.add(findgrade.getTotalPoints());
			}
		}
		
		for(Double num : grades) {
			average += num;
		}
		
		average = average/grades.size();
		
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
		
	private int calculateAge(String date) {
		int age = 0;
		try {
			LocalDate todaysDate = LocalDate.now();
			LocalDate birthDate = LocalDate.parse(date);
			age = (int)Period.between(birthDate, todaysDate).getYears();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return age;

		}
}
