package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PaymentRequest {
    PaymentStatus status;
    double amount;
    LocalDateTime paymentTime;
}
