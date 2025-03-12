package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.validator.EnumPattern;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {
    @NotBlank(message = "Start Time không được để trống")
    LocalDateTime startTime;
    @NotBlank(message = "End Time không được để trống")
    LocalDateTime endTime;
    @EnumPattern(name = "status", regexp = "BOOKED|PENDING")
    BookingStatus status;

    Long userId;
    Long fieldId;
}
