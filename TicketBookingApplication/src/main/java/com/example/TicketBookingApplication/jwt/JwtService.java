package com.example.TicketBookingApplication.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority; // Import needed for Role extraction

@Service
public class JwtService {

    // Inject the secret key from application.properties/yml
    @Value("${jwt.secret}")
    private String jwtKey;

    // Inject expiration time (in milliseconds)
    @Value("${jwt.expiration}")
    private long jwtExpiration; // Changed to long to match System.currentTimeMillis()

    // --- Core Utility Methods ---

    /**
     * Extracts the subject (username) from the token.
     */
    public String extractUsername(String token) {
        // Use the Function lambda to extract the subject
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a single claim from the token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token using the signing key.
     * FIX: Use the key directly in the parser instance.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the expiration date from the token.
     * FIX: Correct return type and usage.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // --- Key Management ---

    /**
     * Generates the HMAC SHA key for signing/verification.
     * FIX: Removed 'jwtKey' parameter; uses the class field.
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // --- Token Generation ---

    /**
     * Generates a token with default claims.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a token with extra claims (like roles/authorities).
     * FIX: Added token signature type argument to signWith.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        // Add roles/authorities as a custom claim
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(java.util.stream.Collectors.joining(","));

        extraClaims.put("authorities", authorities);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // FIX: Use correct jwtExpiration (long) for calculating expiration
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // --- Token Validation ---

    /**
     * Checks if the token is expired.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Checks if the token is valid for the given UserDetails.
     * FIX: Changed extractUsername to use the public method.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}