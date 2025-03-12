package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.nimbusds.jose.JOSEException;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.*;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.TokenResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.UserResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.ForgotPasswordToken;
import com.timesoccer247.Spring_TimeSoccer247.entity.VerificationCodeEntity;
import com.timesoccer247.Spring_TimeSoccer247.service.AuthService;
import com.timesoccer247.Spring_TimeSoccer247.service.ForgotPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest request) throws JOSEException {
        return ApiResponse.<TokenResponse>builder()
                .code(HttpStatus.OK.value())
                .result(authService.login(request))
                .message("Login")
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<TokenResponse> register(@Valid @RequestBody RegisterRequest request) throws JOSEException {
        return ApiResponse.<TokenResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(authService.register(request))
                .message("Register")
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<TokenResponse> refreshToken(@RequestBody TokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<TokenResponse>builder()
                .code(HttpStatus.OK.value())
                .result(authService.refreshToken(request))
                .message("Refresh Token")
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(authService.getMyInfo())
                .message("My Info")
                .build();
    }

    @PostMapping("/change-password")
    public ApiResponse<UserResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        authService.changePassword(request.getOldPassword(), request.getNewPassword());
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(authService.getMyInfo())
                .message("My Info")
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Valid @RequestBody TokenRequest request) throws JOSEException, ParseException {
        authService.logout(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Logout")
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<VerificationCodeEntity> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ApiResponse.<VerificationCodeEntity>builder()
                .code(HttpStatus.OK.value())
                .result(forgotPasswordService.forgotPassword(request))
                .message("Mã xác nhận đã được gửi vào email của bạn")
                .build();
    }

    @PostMapping("/forgot-password/verify-code")
    public ApiResponse<ForgotPasswordToken> verifyCode(@Valid @RequestBody VerifyCodeRequest request) throws JOSEException {
        return ApiResponse.<ForgotPasswordToken>builder()
                .code(HttpStatus.OK.value())
                .result(forgotPasswordService.verifyCode(request.getEmail(), request.getVerificationCode()))
                .message("Mã xác nhận hợp lệ")
                .build();
    }

    @PostMapping("/forgot-password/reset-password")
    public ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        forgotPasswordService.resetPassword(request);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Mật khẩu đã được thay đổi thành công")
                .build();
    }

}
