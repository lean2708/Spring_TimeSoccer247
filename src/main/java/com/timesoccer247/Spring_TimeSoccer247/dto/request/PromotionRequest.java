package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PromotionRequest {
    @Min(value = 0, message = "Discount phải lớn hơn 0")
    double discount;
    LocalDate validUntil;

    Long userId;
}
