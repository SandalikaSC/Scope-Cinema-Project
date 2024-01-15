package com.cinema.authservice.config;

import com.cinema.authservice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //Secret Key
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
//    Expiration time
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;


    //Extract Username From the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


//    Generate Jwt Token
public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    if (userDetails instanceof User) {
        claims.put("userId", ((User) userDetails).getId());
        claims.put("userName", ((User) userDetails).getName());
    }
    return generateToken(claims, userDetails);
}

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

private String buildToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expiration
) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .claim("userId", getUserId(userDetails))
            .claim("userName", getUserName(userDetails))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
}
    private Long getUserId(UserDetails userDetails) {
        if (userDetails instanceof User) {
            System.out.println("user Id: "+((User) userDetails).getId());
            return ((User) userDetails).getId();
        }
        return null;
    }
    private String getUserName(UserDetails userDetails) {
        if (userDetails instanceof User) {
            System.out.println("user Name: "+((User) userDetails).getName());
            return ((User) userDetails).getName();
        }
        return null;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

//    check token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
