package com.timesoccer247.Spring_TimeSoccer247.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenRequest {
    @NotBlank(message = "refreshToken không được để trống")
    String refreshToken;
}
