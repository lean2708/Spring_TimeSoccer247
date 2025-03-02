package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BookingRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BookingResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.FieldResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/bookings")
    public ApiResponse<BookingResponse> addBooking(@RequestBody BookingRequest request){
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
    public ApiResponse<BookingResponse> updateBooking(@PathVariable long id, @RequestBody BookingRequest request){
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
}
