package com.sri.eGameScoreAPI.repository;

import org.springframework.data.repository.CrudRepository;

import com.sri.eGameScoreAPI.entity.Score;

public interface ScoreRepository extends CrudRepository <Score, Long>{

}
