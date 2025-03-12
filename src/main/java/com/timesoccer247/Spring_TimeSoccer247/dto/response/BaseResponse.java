package com.timesoccer247.Spring_TimeSoccer247.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseResponse {
    long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate updatedAt;
    String createdBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String updatedBy;
}
