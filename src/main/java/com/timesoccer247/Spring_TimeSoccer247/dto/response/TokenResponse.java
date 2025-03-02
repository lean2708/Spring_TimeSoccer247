package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponse {
    String email;
    boolean authenticated;
    String accessToken;
    String refreshToken;
    String role;
}
