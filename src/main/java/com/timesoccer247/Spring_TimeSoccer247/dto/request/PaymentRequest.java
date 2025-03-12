package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.validator.EnumPattern;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PaymentRequest {
    @EnumPattern(name = "status", regexp = "PAID|UNPAID")
    PaymentStatus status;
    double amount;
    LocalDateTime paymentTime;

    @NotBlank(message = "BookingID không được để trống")
    Long bookingId;
}
