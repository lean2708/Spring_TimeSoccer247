package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BookingRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BookingResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PromotionResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Booking;
import com.timesoccer247.Spring_TimeSoccer247.entity.Promotion;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.BookingMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.BookingRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.FieldRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.PaymentRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;
    private final PageableService pageableService;
    private final PaymentRepository paymentRepository;

    public BookingResponse addBooking(BookingRequest request) {
        Booking booking = bookingMapper.toBooking(request);

        if(request.getFieldId() != null){
            fieldRepository.findById(request.getFieldId()).ifPresent(booking::setField);
        }

        if(request.getUserId() != null){
            userRepository.findById(request.getUserId()).ifPresent(booking::setUser);
        }

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

        if(request.getFieldId() != null){
            fieldRepository.findById(request.getFieldId()).ifPresent(booking::setField);
        }

        if(request.getUserId() != null){
            userRepository.findById(request.getUserId()).ifPresent(booking::setUser);
        }

        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    @Transactional
    public void deleteBooking(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        if (booking.getPayment() != null) {
            paymentRepository.delete(booking.getPayment());
        }
        if(booking.getUser() != null){
            booking.getUser().getBookings().remove(booking);
            userRepository.save(booking.getUser());
        }

        if(booking.getField() != null){
            booking.getField().getBookings().remove(booking);
            fieldRepository.save(booking.getField());
        }

        bookingRepository.delete(booking);
    }

    public PageResponse<BookingResponse> fetchAllBookings(int pageNo, int pageSize, String sortBy){
        pageNo = pageNo - 1;

        Pageable pageable = pageableService.createPageable(pageNo, pageSize, sortBy);

        Page<Booking> bookingPage = bookingRepository.findAll(pageable);

        List<BookingResponse> responses =  convertListPromotionResponse(bookingPage.getContent());

        return PageResponse.<BookingResponse>builder()
                .page(bookingPage.getNumber() + 1)
                .size(bookingPage.getSize())
                .totalPages(bookingPage.getTotalPages())
                .totalItems(bookingPage.getTotalElements())
                .items(responses)
                .build();
    }

    public List<BookingResponse> convertListPromotionResponse(List<Booking> bookingList){
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for(Booking booking : bookingList){
            BookingResponse response = bookingMapper.toBookingResponse(booking);
            bookingResponseList.add(response);
        }
        return bookingResponseList;
    }
}
