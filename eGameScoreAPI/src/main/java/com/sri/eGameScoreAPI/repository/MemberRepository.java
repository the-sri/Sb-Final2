package com.sri.eGameScoreAPI.repository;

import org.springframework.data.repository.CrudRepository;

import com.sri.eGameScoreAPI.entity.Member;

public interface MemberRepository extends CrudRepository <Member, Long>{
	
	

}
