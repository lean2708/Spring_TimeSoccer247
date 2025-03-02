package com.timesoccer247.Spring_TimeSoccer247.service;

import com.nimbusds.jose.JOSEException;
import com.timesoccer247.Spring_TimeSoccer247.constants.TokenType;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.LoginRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.TokenResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public TokenResponse login(LoginRequest request) throws JOSEException {
        User userDB = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), userDB.getPassword());

        if(!isAuthenticated){
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = tokenService.generateToken(userDB, TokenType.ACCESS_TOKEN);

        return TokenResponse.builder()
                .email(userDB.getEmail())
                .accessToken(accessToken)
                .authenticated(true)
                .build();
    }
}
