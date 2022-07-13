package com.intellinum.ebs.inventory.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.intellinum.ebs.inventory.model.XxfndUser;
import static java.lang.String.format;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {


	private final String jwtSecret = "intellinumEBS";
	//private final String jwtIssuer = "eClarity_App";

	private Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

	public String generateAccessToken(XxfndUser user) {
		return Jwts.builder()
				.setSubject(format("%s,%s",user.getUserId(),user.getUserName()))
				//.setIssuer(jwtIssuer)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserId(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject().split(",")[0];
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject().split(",")[1];
	}

	public Date getExpirationDate(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();

		return claims.getExpiration();
	}

	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature - {}", ex.getMessage());
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token - {}", ex.getMessage());
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token - {}", ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token - {}", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty - {}", ex.getMessage());
		}
		return false;
	}

	//	public String getUsernameFromToken(String token) {
	//		return getClaimFromToken(token, Claims::getSubject);
	//	}
	//
	//	public Date getIssuedAtDateFromToken(String token) {
	//		return getClaimFromToken(token, Claims::getIssuedAt);
	//	}
	//
	//	public Date getExpirationDateFromToken(String token) {
	//		return getClaimFromToken(token, Claims::getExpiration);
	//	}
	//
	//	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	//		final Claims claims = getAllClaimsFromToken(token);
	//		return claimsResolver.apply(claims);
	//	}
	//
	//	private Claims getAllClaimsFromToken(String token) {
	//		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	//	}
	//
	//	private Boolean isTokenExpired(String token) {
	//		final Date expiration = getExpirationDateFromToken(token);
	//		return expiration.before(new Date());
	//	}
	//
	//	private Boolean ignoreTokenExpiration(String token) {
	//		// here you specify tokens, for that the expiration is ignored
	//		return false;
	//	}
	//
	//	public String generateToken(AuthUserDto userDetails) {
	//		Map<String, Object> claims = new HashMap<>();
	//		return doGenerateToken(claims, userDetails.getUsername());
	//	}
	//
	//	private String doGenerateToken(Map<String, Object> claims, String subject) {
	//
	//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	//				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
	//	}
	//
	//	public Boolean canTokenBeRefreshed(String token) {
	//		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	//	}
	//
	//	public Boolean validateToken(String token, UserDetails userDetails) {
	//		final String username = getUsernameFromToken(token);
	//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	//	}

}
