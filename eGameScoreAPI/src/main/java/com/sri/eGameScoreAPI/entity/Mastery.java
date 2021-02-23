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
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Mastery {
	
	private Long id;
	private String gameTitle;
	private String gameDetail;
	
	@JsonIgnore
	private Set<Referee> umpireName;
	
	private String gradeLevel;
	private int maxNumberofMembers;
	
	@JsonIgnore
	private Set<Member> members;
	
	private LocalDate dateGameCreated;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		
		return id;
	}
	
	public void setId(Long id) {
		
		this.id = id;
	}
	
	public String getGameTitle() {
		
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		
		this.gameTitle = gameTitle;
	}
	
	

	public String getGameDetail() {
		return gameDetail;
	}

	public void setGameDetail(String gameDetail) {
		this.gameDetail = gameDetail;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "umpire_game"
	,joinColumns = @JoinColumn(name = "gameId", referencedColumnName = "id")
	,inverseJoinColumns = @JoinColumn(name = "umpireId", referencedColumnName = "id"))
	public Set<Referee> getUmpireName() {
		return umpireName;
	}
	
	
	public void setUmpireName(Set<Referee> umpireName) {
		this.umpireName = umpireName;
	}
	
	public String getGradeLevel() {
		return gradeLevel;
	}
	
	public LocalDate getDateGameCreated() {
		return dateGameCreated;
	}

	public void setDateGameCreated(LocalDate dateGameCreated) {
		this.dateGameCreated = dateGameCreated;
	}

	
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	
	public int getMaxNumberofMembers() {
		return maxNumberofMembers;
	}
	
	public void setMaxNumberofMembers(int maxNumberofMembers) {
		this.maxNumberofMembers = maxNumberofMembers;
	}
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "game_member"
	,joinColumns = @JoinColumn(name = "game", referencedColumnName = "id")
	,inverseJoinColumns = @JoinColumn(name = "member", referencedColumnName = "id"))
	public Set<Member> getMembers() {
		return members;
	}
	
	public void setMembers(Set<Member> members) {
		this.members = members;
	}
}