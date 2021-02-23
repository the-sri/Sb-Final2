package com.sri.eGameScoreAPI.repository;

import org.springframework.data.repository.CrudRepository;

import com.sri.eGameScoreAPI.entity.Coach;

public interface CoachRepository extends CrudRepository <Coach, Long>		{
	
	public Coach findByUsername(String username);

}
