package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class PaymentResponse {
    long id;
    PaymentStatus status;
    double amount;
    LocalDateTime paymentTime;
}
