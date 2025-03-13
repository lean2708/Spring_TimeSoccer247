package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.validator.EnumPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PaymentRequest {
    @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2025-03-13T10:00:00")
    @NotNull(message = "Time không đưược để trống")
    LocalDateTime paymentTime;

    @NotBlank(message = "BookingID không được để trống")
    @Min(value = 0, message = "BookingID phải lớn hơn 0")
    Long bookingId;
}
