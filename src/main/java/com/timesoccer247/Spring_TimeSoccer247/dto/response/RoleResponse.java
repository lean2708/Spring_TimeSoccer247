package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import com.timesoccer247.Spring_TimeSoccer247.dto.basic.PermissionBasic;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse extends BaseResponse {
    String name;
    String description;

    Set<PermissionBasic> permissions;
}
