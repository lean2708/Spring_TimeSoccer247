package com.timesoccer247.Spring_TimeSoccer247.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.timesoccer247.Spring_TimeSoccer247.constants.TokenType;
import com.timesoccer247.Spring_TimeSoccer247.entity.Token;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Value("${jwt.signer-key}")
    private String SIGNER_KEY;
    @Value("${jwt.access-token.expiry-in-minutes}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-key}")
    private String REFRESH_KEY;

    @Value("${jwt.refresh-token.expiry-in-days}")
    private long refreshTokenExpiration;

    @Value("${jwt.reset-key}")
    private String RESET_PASSWORD_KEY;

    @Value("${jwt.reset.expiry-in-minutes}")
    private long resetTokenExpiration;

    // save de token
    public Token saveToken(Token token){
        Optional<Token> optionalToken = tokenRepository.findByEmail(token.getEmail());
        if(optionalToken.isEmpty()){
            return tokenRepository.save(token);
        }
        Token currentToken  = optionalToken.get();
        currentToken.setAccessToken(token.getAccessToken());
        currentToken.setRefreshToken(token.getRefreshToken());
        return currentToken;
    }

    public String generateToken(User user, TokenType type) throws JOSEException {
        // Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Payload
        long durationInSeconds = getDurationByToken(type);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer(user.getName())
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(durationInSeconds)))
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        // Signature
        jwsObject.sign(new MACSigner(getKey(type).getBytes()));

        return jwsObject.serialize();
    }

    public String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }

    private String getKey(TokenType type){
        switch (type){
            case ACCESS_TOKEN -> {return SIGNER_KEY;}
            case REFRESH_TOKEN -> {return REFRESH_KEY;}
            case RESET_PASSWORD_TOKEN -> {return RESET_PASSWORD_KEY;}
            default -> throw new AppException(ErrorCode.TOKEN_TYPE_INVALID);
        }
    }
    private long getDurationByToken(TokenType type) {
        switch (type) {
            case ACCESS_TOKEN -> {return Duration.ofMinutes(accessTokenExpiration).getSeconds();}
            case REFRESH_TOKEN -> {return Duration.ofDays(refreshTokenExpiration).getSeconds();}
            case RESET_PASSWORD_TOKEN -> {return Duration.ofMinutes(resetTokenExpiration).getSeconds();}
            default -> throw new AppException(ErrorCode.TOKEN_TYPE_INVALID);
        }
    }

    public SignedJWT verifyToken(String token, TokenType type) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(getKey(type).getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean isVerified = signedJWT.verify(verifier);

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();

        if(!isVerified || expirationTime.before(new Date()) || jwtId == null){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }


}
