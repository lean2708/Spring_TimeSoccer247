package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest {
    @NotBlank(message = "forgotPasswordToken không được để trống")
    String forgotPasswordToken;
    @Size(min = 5, message = "Password phải từ 5 kí tự trở lên")
    @NotBlank(message = "Password không được để trống")
    String newPassword;
}
