package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class FieldRequest {
    @NotBlank(message = "Name không được để trống")
    String name;
    @Min(value = 0, message = "Price phải lớn hơn 0")
    double price;
}
