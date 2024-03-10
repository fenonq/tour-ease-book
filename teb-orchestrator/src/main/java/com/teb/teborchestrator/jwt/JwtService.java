package com.teb.teborchestrator.jwt;

import com.teb.teborchestrator.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.tokenSecret}")
    private String SECRET_KEY;

    @Value("${jwt.tokenExpirationDays}")
    private long tokenExpirationDays;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        Date expiryDate = java.sql.Date.valueOf(LocalDate.now().plusDays(tokenExpirationDays));
        return Jwts
                .builder()
                .claim("id", userDetails.getId())
                .claim("role", userDetails.getRole())
                .claim("email", userDetails.getEmail())
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}