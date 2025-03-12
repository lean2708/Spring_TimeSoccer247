package com.timesoccer247.Spring_TimeSoccer247.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.timesoccer247.Spring_TimeSoccer247.constants.RoleEnum;
import com.timesoccer247.Spring_TimeSoccer247.constants.TokenType;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.RoleBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.LoginRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.RegisterRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.TokenRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.TokenResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.UserResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Role;
import com.timesoccer247.Spring_TimeSoccer247.entity.Token;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.RoleMapper;
import com.timesoccer247.Spring_TimeSoccer247.mapper.UserMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.RoleRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.TokenRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public TokenResponse login(LoginRequest request) throws JOSEException {
        User userDB = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), userDB.getPassword());

        if(!isAuthenticated){
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        Set<RoleBasic> roleBasics = roleMapper.toRoleBasics(userDB.getRoles());

        String accessToken = tokenService.generateToken(userDB, TokenType.ACCESS_TOKEN);

        String refreshToken = tokenService.generateToken(userDB, TokenType.REFRESH_TOKEN);

        tokenService.saveToken(Token.builder()
                .email(userDB.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .email(userDB.getEmail())
                .role(roleBasics)
                .build();
    }

    public TokenResponse register(RegisterRequest request) throws JOSEException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleEnum.USER.name()).orElseThrow(
                () -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        Set<Role> roles = new HashSet<>();
        if(user.getRoles() != null){
            roles.addAll(user.getRoles());
        }
        roles.add(userRole);
        user.setRoles(roles);

//        emailService.sendUserEmailWithRegister(user);
        userRepository.save(user);

        Set<RoleBasic> roleBasics = roleMapper.toRoleBasics(user.getRoles());

        String accessToken = tokenService.generateToken(user, TokenType.ACCESS_TOKEN);

        String refreshToken = tokenService.generateToken(user, TokenType.REFRESH_TOKEN);

        tokenService.saveToken(Token.builder()
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .email(user.getEmail())
                .role(roleBasics)
                .build();
    }

    public String getCurrentUsername(){
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return authentication.getName(); // email
    }

    public TokenResponse refreshToken(TokenRequest request) throws ParseException, JOSEException {
        // check token
        Token token = tokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));

        // verify refresh token
        SignedJWT signedJWT = tokenService.verifyToken(request.getRefreshToken(), TokenType.REFRESH_TOKEN);

        String email = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // new access token
        String accessToken = tokenService.generateToken(user, TokenType.ACCESS_TOKEN);
        token.setAccessToken(accessToken);

        tokenService.saveToken(token);

        Set<RoleBasic> roleBasics = roleMapper.toRoleBasics(user.getRoles());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .email(email)
                .role(roleBasics)
                .build();
    }

    public UserResponse getMyInfo() {
        User user = userRepository.findByEmail(getCurrentUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public void changePassword(String oldPassword, String newPassword){
        User user = userRepository.findByEmail(getCurrentUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_OLD_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void logout(TokenRequest request) {
        // check token
        Token token = tokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));

        tokenRepository.delete(token);
    }


}
