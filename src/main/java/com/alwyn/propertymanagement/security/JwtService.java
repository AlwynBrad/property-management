package com.alwyn.propertymanagement.security;
import java.security.Key;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

    @Value("${SECRET_KEY}")
    private String secretKey;
    // Method to obtain the signing key from the provided secret key.


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate a JWT token for the given subject (user) with a specific expiration time.
    public String generateJwtToken(String subject) throws JOSEException {
        try {
            Key signingKey = getSigningKey();

            //JWTClaimsSet with the desired claims
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(subject)
                    .expirationTime(new Date(System.currentTimeMillis() + 3600000 * 2)) // 2 hour expiration
                    .build();

            //JWS header with HMAC using SHA-256
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT)
                    .build();

            //signed JWT token
            SignedJWT signedJWT = new SignedJWT(header, claimsSet);

            // Signing the JWT using the signing key
            signedJWT.sign(new MACSigner(signingKey.getEncoded()));

            // Serializing the JWT token to a string
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new JOSEException("Error generating JWT token", e);
        }
    }

    // Extract the username (subject) from a JWT token.
    public String extractUsername(String token) throws JOSEException {
        try {
            Key signingKey = getSigningKey();

            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(signingKey.getEncoded());

            // Verify the JWT token's signature.
            if (!signedJWT.verify(verifier)) {
                throw new JOSEException("JWT verification failed");
            }

            // Get the claims from the JWT and return the subject (username).
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return claims.getSubject();
        } catch (ParseException e) {
            throw new JOSEException("JWT parsing error", e);
        }
    }

     // Check if a JWT token is valid for a given user's details.
    public boolean isTokenValid(String token, UserDetails userDetails) throws JOSEException{
        String userName = extractUsername(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Check if a JWT token is expired by comparing its expiration time with the current time.
    private boolean isTokenExpired(String token) throws JOSEException {
        return extractExpiration(token).before(new Date());
    }

    // Extract the expiration time from a JWT token's claims.
    private Date extractExpiration(String token) throws JOSEException {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return claimsSet.getExpirationTime();
        } catch (ParseException e) {
            throw new JOSEException("Error parsing JWT token", e);
        }
    }
    
}



