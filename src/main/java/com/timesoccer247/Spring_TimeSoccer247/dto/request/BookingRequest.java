package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
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
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {
    @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2025-03-13T10:00:00")
    @NotNull(message = "Start Time không được để trống")
    LocalDateTime startTime;
    @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2025-03-13T11:00:00")
    @NotNull(message = "End Time không được để trống")
    LocalDateTime endTime;

    @NotNull(message = "FieldId không được để trống")
    @Min(value = 0, message = "FieldId phải lớn hơn 0")
    Long fieldId;

    Set<Long> ballIds;
}
