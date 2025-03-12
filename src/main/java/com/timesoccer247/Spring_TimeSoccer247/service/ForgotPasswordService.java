package com.timesoccer247.Spring_TimeSoccer247.service;

import com.nimbusds.jose.JOSEException;
import com.timesoccer247.Spring_TimeSoccer247.constants.TokenType;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.ForgotPasswordRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.ResetPasswordRequest;
import com.timesoccer247.Spring_TimeSoccer247.entity.ForgotPasswordToken;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import com.timesoccer247.Spring_TimeSoccer247.entity.VerificationCodeEntity;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.repository.ForgotPasswordTokenRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final ForgotPasswordTokenRepository forgotPasswordTokenRepository;
    private final TokenService tokenService;

    @Value("${jwt.reset.expiry-in-minutes}")
    protected long resetTokenExpiration;

    public VerificationCodeEntity forgotPassword(ForgotPasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String verificationCode = generateVerificationCode();
        try {
            emailService.sendVerificationCode(user, verificationCode);

            long expirationTimeInMinutes = System.currentTimeMillis() / 60000 + (5);

            VerificationCodeEntity verificationCodeEntity = VerificationCodeEntity.builder()
                    .email(user.getEmail())
                    .verificationCode(verificationCode)
                    .expirationTime(expirationTimeInMinutes)
                    .build();

            return saveVerificationCode(verificationCodeEntity);
        } catch (Exception e) {
            log.error("Lỗi gửi email: ", e);
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    private VerificationCodeEntity saveVerificationCode(VerificationCodeEntity code){
        Optional<VerificationCodeEntity> entityOptional = verificationCodeRepository.findByEmail(code.getEmail());
        if(entityOptional.isEmpty()){
            return verificationCodeRepository.save(code);
        }
        VerificationCodeEntity entity = entityOptional.get();
        entity.setVerificationCode(code.getVerificationCode());
        entity.setExpirationTime(code.getExpirationTime());

        return entity;
    }

    public ForgotPasswordToken verifyCode(String email, String verificationCode) throws JOSEException {
            VerificationCodeEntity verificationCodeEntity = verificationCodeRepository.findByEmailAndVerificationCode(email, verificationCode)
                    .orElseThrow(() -> new AppException(ErrorCode.VERIFICATION_CODE_NOT_FOUND));

            if (verificationCodeEntity.getExpirationTime() < System.currentTimeMillis() / 60000) {
                throw new AppException(ErrorCode.VERIFICATION_CODE_EXPIRED);
            }

            User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

            String forgotPasswordToken = tokenService.generateToken(user, TokenType.RESET_PASSWORD_TOKEN);
            ForgotPasswordToken token = ForgotPasswordToken.builder()
                    .email(email)
                    .forgotPasswordToken(forgotPasswordToken)
                    .expiryTime(LocalDateTime.now().plusMinutes(resetTokenExpiration))
                    .build();

            verificationCodeRepository.delete(verificationCodeEntity);

            return forgotPasswordTokenRepository.save(token);
    }

    public void resetPassword(ResetPasswordRequest request) {
        try {
            tokenService.verifyToken(request.getForgotPasswordToken(), TokenType.RESET_PASSWORD_TOKEN);
        } catch (JOSEException | ParseException e) {
            throw new BadJwtException(e.getMessage());
        } catch (AppException ex){
            throw new BadJwtException("Token không hợp lệ");
        }
        ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenRepository
                .findByForgotPasswordToken(request.getForgotPasswordToken())
                .orElseThrow( () -> new AppException(ErrorCode.FORGOT_PASSWORD_TOKEN_NOT_FOUND));

        User user = userRepository.findByEmail(forgotPasswordToken.getEmail()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        forgotPasswordTokenRepository.delete(forgotPasswordToken);
    }

    private String generateVerificationCode() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

}
