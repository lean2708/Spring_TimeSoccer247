package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.BookingBasic;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

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

    BookingBasic booking;
}
