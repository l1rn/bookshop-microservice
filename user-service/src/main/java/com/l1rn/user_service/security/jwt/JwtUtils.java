package com.l1rn.user_service.security.jwt;

import com.l1rn.user_service.models.entity.user.Role;
import com.l1rn.user_service.models.entity.user.Status;
import com.l1rn.user_service.models.entity.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtils {
    @Value("jwt.token.refresh.name")
    private String jwtToken;
    @Value("jwt.token.refresh.expiration")
    private Integer jwtExpiration;
    Logger logger;

    public String generateToken(UserEntity user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles()
                        .stream()
                        .map(Role::getId)
                        .toList())
                .claim("statuses", user.getStatuses()
                        .stream()
                        .map(Status::getId)
                        .toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsRevolver){
        final Claims claims = extractAllClaims(token);
        return claimsRevolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token){
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (RuntimeException e) {
            logger.info("TOKEN EXPIRED BRO 🥹😢😭😿🥲");
            throw new RuntimeException();
        }
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = Base64.getDecoder()
                .decode(jwtToken.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String getEmailFromClaim(String token){
        return extractClaim(token, Claims::getSubject);
    }

}
