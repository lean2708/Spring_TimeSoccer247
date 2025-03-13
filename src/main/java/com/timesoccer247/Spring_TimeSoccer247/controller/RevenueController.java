package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.RevenueResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class RevenueController {
    private final RevenueService revenueService;

    @GetMapping("/revenues/day")
    public ApiResponse<RevenueResponse> getRevenueByDay(@RequestParam Long fieldId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ApiResponse.<RevenueResponse>builder()
                .code(HttpStatus.OK.value())
                .result(revenueService.getRevenueByDay(fieldId, date))
                .message("Fetch Revenue By Day")
                .build();
    }

    @GetMapping("/revenues/month")
    public ApiResponse<RevenueResponse> getRevenueByMonth(@RequestParam Long fieldId,
                                                          @RequestParam int year,
                                                          @RequestParam int month) {
        return ApiResponse.<RevenueResponse>builder()
                .code(HttpStatus.OK.value())
                .result(revenueService.getRevenueByMonth(fieldId, year, month))
                .message("Fetch Revenue By Month")
                .build();
    }

    @GetMapping("/revenues/year")
    public ApiResponse<RevenueResponse> getRevenueByYear(@RequestParam Long fieldId,
                                                         @RequestParam int year) {
        return ApiResponse.<RevenueResponse>builder()
                .code(HttpStatus.OK.value())
                .result(revenueService.getRevenueByYear(fieldId, year))
                .message("Fetch Revenue By Year")
                .build();
    }
}
