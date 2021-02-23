package com.sri.eGameScoreAPI.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class Score {

	private Long id;
	private Long gameid;
	private Long matchid;
	private Long memberid;
	private Double matchpoint;
	private Double totalPoints;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getGameid() {
		return gameid;
	}

	public void setGameid(Long gameid) {
		this.gameid = gameid;
	}

	public Long getMatchid() {
		return matchid;
	}
	
	public void setMatchid(Long matchid) {
		this.matchid = matchid;
	}
	
	public Long getMemberid() {
		return memberid;
	}
	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}
	
	public Double getMatchpoint() {
		return matchpoint;
	}
	
	public void setMatchpoint(Double matchpoint) {
		this.matchpoint = matchpoint;
	}

	public Double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Double totalPoints) {
		this.totalPoints = totalPoints;
	}
}