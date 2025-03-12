package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.timesoccer247.Spring_TimeSoccer247.constants.GenderEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class UserRequest {
    String name;
    String email;
    String password;
    String phone;
    GenderEnum gender;
    String avatarUrl;

    Set<Long> roleIds;
}
