package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class BallRequest {
    @NotBlank(message = "Name không được để trống")
    String name;
    int quantity;
    String type;
    @NotBlank(message = "Price không được để trống")
    double price;

    Long fieldId;
}
