package com.sri.eGameScoreAPI.repository;

import org.springframework.data.repository.CrudRepository;

import com.sri.eGameScoreAPI.entity.Tourney;

public interface TourneyRepository extends CrudRepository <Tourney, Long>	{

}
