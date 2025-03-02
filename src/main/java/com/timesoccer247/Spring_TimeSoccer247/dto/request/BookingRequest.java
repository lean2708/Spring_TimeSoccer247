package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {
    LocalDateTime startTime;
    LocalDateTime endTime;
    BookingStatus status;
}
