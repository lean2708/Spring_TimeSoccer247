package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VNPayResponse {
    String code;
    String message;
    String paymentUrl;
}
