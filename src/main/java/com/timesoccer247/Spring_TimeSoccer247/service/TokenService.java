package com.timesoccer247.Spring_TimeSoccer247.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.timesoccer247.Spring_TimeSoccer247.constants.TokenType;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenService {

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;
    @Value("${jwt.accessToken.expiry-in-minutes}")
    private long accessTokenExpiration;

    public String generateToken(User user, TokenType type) throws JOSEException {
        // header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //payload
        long duration = Duration.ofMinutes(accessTokenExpiration).getSeconds();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer(user.getName())
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(duration)))
//                .claim()
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        // Signature
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

        return jwsObject.serialize();
    }

}
