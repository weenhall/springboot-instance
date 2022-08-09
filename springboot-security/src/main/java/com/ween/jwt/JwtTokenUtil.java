package com.ween.jwt;

import java.util.Date;
import io.jsonwebtoken.*;
import com.ween.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Component
public class JwtTokenUtil {

	@Value("${app.jwt.secret}")
	private String secretKey;

	private static final Long EXPIRE_DURATION = (long) (24 * 60 * 60 * 1000); // 24 hr

	public String generateAccessToken(User user){
		return Jwts.builder()
				.setSubject("user"+user.getId())
				.setIssuer("isuser")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS256,secretKey)
				.compact();
	}

	public Claims parseClaims(String token){
		return Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}

	public String getSubject(String token){
		return parseClaims(token).getSubject();
	}

	public boolean validateAccessToken(String token){
		try{
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch (ExpiredJwtException ex){
			log.error("Expired");
		}catch (IllegalArgumentException ex){
			log.error("Token is null, empty or has only whitepace");
		}catch (MalformedJwtException ex){
			log.error("invalid token");
		}catch (UnsupportedJwtException ex){
			log.error("jwt unsupported");
		}catch (SignatureException ex){
			log.error("invalid signature");
		}
		return false;
	}
}
