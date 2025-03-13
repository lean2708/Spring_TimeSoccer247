package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class RevenueResponse {
    private Long fieldId;
    private String fieldName;
    private double amount;
    private LocalDateTime timeRange;
}
