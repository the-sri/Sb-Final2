package com.sri.eGameScoreAPI.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tourney {
	
	private Long id;
	private String description;
	private LocalDate date;
	private double totalPoints;
	
	@JsonIgnore 
	private Mastery gameroom;
	
	@JsonIgnore 
	private Coach coach;
	
	@JsonIgnore 
	private Set<Member> members;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}

	@ManyToOne
	@JoinColumn(name = "classlevel")
	public Mastery getGameroom() {
		return gameroom;
	}

	public void setGameroom(Mastery gameroom) {
		this.gameroom = gameroom;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "match_member"
			,joinColumns = @JoinColumn(name = "matchid", referencedColumnName = "id")
			,inverseJoinColumns = @JoinColumn(name = "memberid", referencedColumnName = "id"))
	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

}