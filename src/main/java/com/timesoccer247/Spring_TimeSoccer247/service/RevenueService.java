package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.RevenueResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.repository.FieldRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class RevenueService {
    private final PaymentRepository paymentRepository;
    private final FieldRepository fieldRepository;

    public RevenueResponse getRevenueByDay(Long fieldId, LocalDate date) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(()-> new AppException(ErrorCode.FIELD_NOT_EXISTED));

        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.atTime(LocalTime.MAX);
        double amount =  paymentRepository.getTotalRevenueByTimeRangeForField(fieldId, startTime, endTime, PaymentStatus.PAID);

        return RevenueResponse.builder()
                .fieldId(fieldId)
                .fieldName(field.getName())
                .amount(amount)
                .timeRange(LocalDateTime.now())
                .build();
    }

    public RevenueResponse getRevenueByMonth(Long fieldId, int year, int month) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(()-> new AppException(ErrorCode.FIELD_NOT_EXISTED));

        LocalDateTime startTime = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime endTime = startTime.plusMonths(1).minusSeconds(1);
        double amount = paymentRepository.getTotalRevenueByTimeRangeForField(fieldId, startTime, endTime, PaymentStatus.PAID);

        return RevenueResponse.builder()
                .fieldId(fieldId)
                .fieldName(field.getName())
                .amount(amount)
                .timeRange(LocalDateTime.now())
                .build();
    }

    public RevenueResponse getRevenueByYear(Long fieldId, int year) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(()-> new AppException(ErrorCode.FIELD_NOT_EXISTED));

        LocalDateTime startTime = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endTime = startTime.plusYears(1).minusSeconds(1);
        double amount = paymentRepository.getTotalRevenueByTimeRangeForField(fieldId, startTime, endTime, PaymentStatus.PAID);

        return RevenueResponse.builder()
                .fieldId(fieldId)
                .fieldName(field.getName())
                .amount(amount)
                .timeRange(LocalDateTime.now())
                .build();
    }
}
