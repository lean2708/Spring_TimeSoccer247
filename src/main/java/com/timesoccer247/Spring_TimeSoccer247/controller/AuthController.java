package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.nimbusds.jose.JOSEException;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.LoginRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.TokenResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest request) throws JOSEException {
        return ApiResponse.<TokenResponse>builder()
                .code(HttpStatus.OK.value())
                .result(authService.login(request))
                .message("Login")
                .build();
    }
}
