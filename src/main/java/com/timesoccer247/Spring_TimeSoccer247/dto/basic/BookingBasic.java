package com.timesoccer247.Spring_TimeSoccer247.dto.basic;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingBasic {
    long id;
    String name;
}
