package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class UserRequest {
    String name;
    String email;
    String password;
    String phone;
    String avatarUrl;
}
