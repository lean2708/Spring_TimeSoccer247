package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BookingRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.*;
import com.timesoccer247.Spring_TimeSoccer247.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/bookings")
    public ApiResponse<BookingResponse> addBooking(@Valid @RequestBody BookingRequest request){
        return ApiResponse.<BookingResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(bookingService.addBooking(request))
                .message("Create Booking")
                .build();
    }

    @GetMapping("/bookings/{id}")
    public ApiResponse<BookingResponse> getBookingById(@PathVariable long id){
        return ApiResponse.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .result(bookingService.getBookingById(id))
                .message("Fetch Booking By Id")
                .build();
    }

    @PutMapping("/bookings/{id}")
    public ApiResponse<BookingResponse> updateBooking(@PathVariable long id,@Valid @RequestBody BookingRequest request){
        return ApiResponse.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .result(bookingService.updateBooking(id, request))
                .message("Update Booking")
                .build();
    }

    @DeleteMapping("/bookings/{id}")
    public ApiResponse<Void> deleteBooking(@PathVariable long id){
        bookingService.deleteBooking(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .result(null)
                .message("Delete Booking")
                .build();
    }

    @GetMapping("/bookings")
    public ApiResponse<PageResponse<BookingResponse>> fetchAll(@Min(value = 1, message = "pageNo phải lớn hơn 0")
                                                            @RequestParam(defaultValue = "1") int pageNo,
                                                            @RequestParam(defaultValue = "10") int pageSize,
                                                            @Pattern(regexp = "^(\\w+?)(-)(asc|desc)$", message = "Định dạng của sortBy phải là: field-asc hoặc field-desc")
                                                            @RequestParam(required = false) String sortBy){
        return ApiResponse.<PageResponse<BookingResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(bookingService.fetchAllBookings(pageNo, pageSize, sortBy))
                .message("Fetch All Bookings With Pagination")
                .build();
    }

    @GetMapping("/bookings/field/{fieldId}")
    public ApiResponse<PageResponse<BookingDetailResponse>> getBookedTimes(
            @PathVariable Long fieldId,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate date,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        return ApiResponse.<PageResponse<BookingDetailResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(bookingService.getBookedTimesByField(fieldId, date, pageNo, pageSize))
                .message("Fetch bookings for field on selected date with pagination")
                .build();
    }
}
