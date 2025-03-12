package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@SuperBuilder
public class PermissionResponse {

    String name;
    String module;
    String apiPath;
    String method;
}
