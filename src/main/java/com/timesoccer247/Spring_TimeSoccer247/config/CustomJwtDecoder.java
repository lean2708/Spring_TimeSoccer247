package com.timesoccer247.Spring_TimeSoccer247.config;

import com.nimbusds.jose.JOSEException;
import com.timesoccer247.Spring_TimeSoccer247.constants.TokenType;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@RequiredArgsConstructor
@Component
public class CustomJwtDecoder implements JwtDecoder {

    private final TokenService tokenService;
    private final NimbusJwtDecoder nimbusJwtDecoder;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            tokenService.verifyToken(token, TokenType.ACCESS_TOKEN);
        } catch (JOSEException | ParseException e) {
            throw new BadJwtException(e.getMessage());
        } catch (AppException ex){
            throw new BadJwtException("Token không hợp lệ");
        }
        return nimbusJwtDecoder.decode(token);
    }

}