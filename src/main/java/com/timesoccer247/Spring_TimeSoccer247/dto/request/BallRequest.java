package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class BallRequest {
    String name;
    int quantity;
    String type;
}
