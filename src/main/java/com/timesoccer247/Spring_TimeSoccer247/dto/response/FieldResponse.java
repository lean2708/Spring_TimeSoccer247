package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class FieldResponse {
    long id;
    String name;
    double price;
}
