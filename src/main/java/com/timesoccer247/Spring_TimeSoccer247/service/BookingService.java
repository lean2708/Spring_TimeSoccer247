package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.BookingRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BookingDetailResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BookingResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PromotionResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.*;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.BookingMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;
    private final PageableService pageableService;
    private final PaymentRepository paymentRepository;
    private final BallRepository ballRepository;
    private final AuthService authService;

    public BookingResponse addBooking(BookingRequest request) {
        Booking booking = bookingMapper.toBooking(request);
        booking.setStatus(BookingStatus.PENDING);

        if(request.getFieldId() != null){
            Field field = fieldRepository.findById(request.getFieldId())
                    .orElseThrow(()-> new AppException(ErrorCode.FIELD_NOT_EXISTED));

            if(bookingRepository.countByFieldAndTimeRangeAndStatus(field.getId(),
                    booking.getStartTime(), booking.getEndTime(), BookingStatus.BOOKED) > 0){
                throw new AppException(ErrorCode.FIELD_ALREADY_BOOKED);
            }
            booking.setField(field);
        }

        // ball
        if(!CollectionUtils.isEmpty(request.getBallIds())){
            Set<Ball> ballSet = ballRepository.findAllByIdIn(request.getBallIds());

            if(ballSet.isEmpty()){
                throw new AppException(ErrorCode.BALL_NOT_FOUND);
            }

            Set<Long> ballIds = ballSet.stream()
                    .map(BaseEntity::getId).collect(Collectors.toSet());

            if(bookingRepository.countByBallsAndTimeRangeAndStatus(
                    ballIds, booking.getStartTime(), booking.getEndTime(),
                    BookingStatus.BOOKED) > 0){
                throw new AppException(ErrorCode.BALL_ALREADY_BOOKED);
            }
            booking.setBalls(ballSet);
        }

        userRepository.findByEmail(authService.getCurrentUsername()).ifPresent(booking::setUser);

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

        userRepository.findByEmail(authService.getCurrentUsername()).ifPresent(booking::setUser);

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

    public PageResponse<BookingDetailResponse> getBookedTimesByField(Long fieldId, LocalDate date, int pageNo, int pageSize) {
        pageNo = pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        Page<Object[]> bookingPage = bookingRepository.findBookedTimesByField(fieldId, startOfDay, endOfDay, BookingStatus.BOOKED, pageable);

        List<BookingDetailResponse> bookings = bookingPage.getContent().stream().map(obj ->
                BookingDetailResponse.builder()
                        .bookingId(((Number) obj[0]).longValue())
                        .startTime(((LocalDateTime) obj[1]))
                        .endTime(((LocalDateTime) obj[2]))
                        .bookedBy((String) obj[3])
                        .status((BookingStatus) obj[4])
                        .build()
        ).toList();

        return PageResponse.<BookingDetailResponse>builder()
                .page(bookingPage.getNumber() + 1) // Trả về số trang đúng
                .size(bookingPage.getSize())
                .totalPages(bookingPage.getTotalPages())
                .totalItems(bookingPage.getTotalElements())
                .items(bookings)
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
