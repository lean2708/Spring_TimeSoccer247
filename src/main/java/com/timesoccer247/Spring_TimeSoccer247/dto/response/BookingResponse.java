package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.FieldBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.UserBasic;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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
}
