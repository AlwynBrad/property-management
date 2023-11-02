package com.alwyn.propertymanagement.security;

import com.alwyn.propertymanagement.entity.UserEntity;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {JwtService.class})
@ExtendWith(SpringExtension.class)
@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties
class JwtServiceTest {
    @Autowired
    private JwtService jwtService;

    private String secretKey = "6d90fe4dc667c98cc034ae3548a7429f081941616c8b44e6e56f42a47ba15327";

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTg2NzI2MjYsInN1YiI6ImFsd3luQGdtYWlsLmNvbSJ9.f4MranC0_cLHmt6dzgVxRauP1FchB9jg8B3NGhc0bYo";

    /**
     * Method under test: {@link JwtService#generateJwtToken(String)}
     */
    @Test
    void testGenerateJwtToken() throws JOSEException, ParseException {
        String subject = "testsubject@gmail.com";
        String generatedToken = jwtService.generateJwtToken(subject);

        // Manually creating a JWSVerifier using the secret key
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        JWSVerifier verifier = new MACVerifier(keyBytes);

        SignedJWT signedJWT = SignedJWT.parse(generatedToken);

        assertTrue(signedJWT.verify(verifier));

        // Assert that the subject of the token matches the provided subject
        assertEquals(subject, signedJWT.getJWTClaimsSet().getSubject());
    }
    /**
     * Method under test: {@link JwtService#extractUsername(String)}
     */
    @Test
    void testExtractUsername() throws JOSEException {
        String validToken = token;

        String extractedUsername = jwtService.extractUsername(validToken);
        
        assertEquals("alwyn@gmail.com", extractedUsername);
    }


    /**
     * Method under test: {@link JwtService#isTokenValid(String, UserDetails)}
     */
    @Test
    void testIsTokenValid() throws JOSEException {
        String validToken = token;

        UserEntity userEntity = new UserEntity();
        userEntity.setOwnerEmail("alwyn@gmail.com");

        boolean isTokenValid = jwtService.isTokenValid(validToken, userEntity);

        assertTrue(isTokenValid);
    }

}

