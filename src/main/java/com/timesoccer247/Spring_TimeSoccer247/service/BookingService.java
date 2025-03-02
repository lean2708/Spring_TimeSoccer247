package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BookingRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BookingResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Booking;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.BookingMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse addBooking(BookingRequest request) {
        Booking booking = bookingMapper.toBooking(request);

        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    public BookingResponse getBookingById(long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        return bookingMapper.toBookingResponse(booking);
    }

    public BookingResponse updateBooking(long id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        bookingMapper.updateBooking(booking, request);

        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    public void deleteBooking(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        bookingRepository.delete(booking);
    }
}
