package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PermissionRequest {
    @NotBlank(message = "Name không được để trống")
    String name;

    @NotBlank(message = "Module không được để trống")
    String module;

    @NotBlank(message = "ApiPath không được để trống")
    String apiPath;

    @NotBlank(message = "Method không được để trống")
    String method;
}
