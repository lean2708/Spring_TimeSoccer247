package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.BallBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.FieldBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.UserBasic;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {
    long id;
    LocalDateTime startTime;
    LocalDateTime endTime;
    BookingStatus status;

    UserBasic user;
    FieldBasic field;
    Set<BallBasic> balls;
}
