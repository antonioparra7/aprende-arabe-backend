package com.aprendearabe.backend.app.auth.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	private static final String SECRET_KEY = "4WXF4BC2WLRML5DMLM26ARCKG39ZM9KUFHFQFTGCPDM4BZ7VV98XH9K4264675X9GXU72ZP8Q5L77CMXJLAA6VH7X743FAJ5393RE5CWWQNCVBWBYHLG8KHTQE8838YTJSCJJXAVPKTXSYSELWWNF4295SJA4A8F8ZRXQVT7QAFB4368Z36C8TGTC2R7XP426PEXZYUVXEH7GTCPBA5HP82HPNDBT6D3TKZ4FJ4MBCVGA7DEFDXXG4G2EM23YWPW";
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
	}
	
	public String generateToken(Map<String, Object> extraClaims,UserDetails userDetails) {
		// 24 HOURS TOKEN
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+1000*60*24)).signWith(getSignInKey(),SignatureAlgorithm.HS256).compact();
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
