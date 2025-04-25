package ru.skypro.homework.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Duration jwtLifeTime;

    public String generateToken(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<String> role = List.of(userDetails.getAuthorities().toString());

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtLifeTime.toMillis());

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getSignInKey())            // вот тут уже сложности
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsernameFromToken(token);

        return (username != null && username.equals(userDetails.getUsername()));
    }

    public String extractUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        List<String> role = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        claims.put("role", role);
//
//        Date currentDate = new Date();
//        Date expirationDate = new Date(currentDate.getTime() + jwtLifeTime.toMillis());
//        byte[] secretInBytes = secret.getBytes(StandardCharsets.UTF_8);
//        SecretKey secretKey = Keys.hmacShaKeyFor(secretInBytes);
//
//        return Jwts.builder()
//                .claims(claims)
//                .subject(userDetails.getUsername())
//                .issuedAt(currentDate)
//                .expiration(expirationDate)
//                .signWith(secretKey)            // вот тут уже сложности
//                .compact();
//    }
//
//
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsername(token);
//        return (username != null && username.equals(userDetails.getUsername()));
//    }
//
//    public String getUsername(String token) {
//        return getAllClaims(token).getSubject();
//    }
//
//    public String getRole(String token) {
//        return getAllClaims(token)
//                .get("role", List.class)
//                .stream()
//                .findFirst()
//                .toString();
//    }
//
//    private Claims getAllClaims(String token) {
//        byte[] secretInBytes = secret.getBytes(StandardCharsets.UTF_8);
//        SecretKey secretKey = Keys.hmacShaKeyFor(secretInBytes);
//
//        return Jwts.parser()
//                .decryptWith(secretKey)
//                .build()
//                .parseEncryptedClaims(token)
//                .getPayload();                                                        //  оно?
//    }

}
