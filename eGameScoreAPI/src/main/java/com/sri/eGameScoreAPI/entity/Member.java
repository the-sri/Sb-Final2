package com.sri.eGameScoreAPI.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Member {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String birthDate;
	private int age;
	private Set<Mastery> getGame;
	private String gradeLevel;
	private String points;
	
	@JsonIgnore
	private Coach coach;
	
	
	private Set<Tourney> match;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	
	@ManyToMany(mappedBy = "members")
	public Set<Mastery> getGame() {
		return getGame;
	}


	public void setGame(Set<Mastery> getGame) {
		this.getGame = getGame;
	}


	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
	@ManyToOne
	@JoinColumn(name = "coachId")
	public Coach getCoach() {
		return coach;
	}
	
	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	@ManyToMany(mappedBy = "members")
	public Set<Tourney> getMatch() {
		return match;
	}

	public void setMatch(Set<Tourney> match) {
		this.match = match;
	}
}