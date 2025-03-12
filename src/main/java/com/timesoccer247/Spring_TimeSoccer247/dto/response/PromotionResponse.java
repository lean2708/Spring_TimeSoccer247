package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import com.timesoccer247.Spring_TimeSoccer247.dto.basic.UserBasic;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class PromotionResponse {
    long id;
    double discount;
    LocalDate validUntil;

    UserBasic user;
}
