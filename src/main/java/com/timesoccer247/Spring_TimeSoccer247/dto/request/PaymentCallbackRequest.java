package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentCallbackRequest {
    @NotBlank(message = "Response Code không được để trống")
    String responseCode;
    @Positive(message = "Amount phải lớn hơn 0")
    long amount;

    @NotNull(message = "BookingId không được để trống")
    long bookingId;
}
