package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PromotionRequest {
    double discount;
    LocalDate validUntil;

    Long userId;
}
