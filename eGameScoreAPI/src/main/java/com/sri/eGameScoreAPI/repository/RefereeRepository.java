package com.sri.eGameScoreAPI.repository;

import org.springframework.data.repository.CrudRepository;

import com.sri.eGameScoreAPI.entity.Referee;

public interface RefereeRepository extends CrudRepository <Referee, Long>		{
	public Referee findByUsername(String username);
}
