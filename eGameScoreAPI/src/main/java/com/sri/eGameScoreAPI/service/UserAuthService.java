package com.sri.eGameScoreAPI.service;

import java.security.Key;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.sri.eGameScoreAPI.entity.Referee;
import com.sri.eGameScoreAPI.util.AccountLevel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class UserAuthService {
	Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
	public boolean isCorrectAccountLevel(String jwt, Long account) {
		return new Long((Integer)Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(jwt)
				.getBody()
				.get("account"))
				.equals(account);
	}
	
	public String getToken(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		if(header == null) {
			throw new Exception("request contains no token.");
		}
		
		return header.replaceAll("Bearer ", "");
	}
	
	public String generateToken(Referee umpire) {
		String account = AccountLevel.UMPIRE.toString();
		String jwt = Jwts.builder()
					.claim("role", account)
					.claim("umpireId", umpire.getId())
					.setSubject("Game Score Management")
					.signWith(key)
					.compact();
		return jwt;
	}
	
	public String passwordHash(String password) {
		String pass = password.toString();
		String hash = BCrypt.hashpw(pass, BCrypt.gensalt());
		
		return hash;
	}
}