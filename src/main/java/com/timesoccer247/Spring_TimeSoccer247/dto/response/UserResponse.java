package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import com.timesoccer247.Spring_TimeSoccer247.constants.GenderEnum;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.RoleBasic;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class UserResponse extends BaseResponse{

    String name;
    String email;
    String phone;
    String avatarUrl;
    @Enumerated(EnumType.STRING)
    GenderEnum gender;
    String address;

    Set<RoleBasic> roles;
}
