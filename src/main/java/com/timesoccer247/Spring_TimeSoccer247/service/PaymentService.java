package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.config.VNPAYConfig;
import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.PaymentCallbackRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.*;
import com.timesoccer247.Spring_TimeSoccer247.entity.*;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.FieldMapper;
import com.timesoccer247.Spring_TimeSoccer247.mapper.PaymentMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.BookingRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.FieldRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.PaymentRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import com.timesoccer247.Spring_TimeSoccer247.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PageableService pageableService;
    private final VNPAYConfig vnpayConfig;
    private final FieldRepository fieldRepository;


    public VNPayResponse createVnPayPayment(Long bookingId, HttpServletRequest request) {
        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        double ballPrice = 0;
        if(!CollectionUtils.isEmpty(booking.getBalls())){
            ballPrice = booking.getBalls().stream()
                    .mapToDouble(Ball::getPrice)
                    .sum();
        }

        long amount = (long)((booking.getField().getPrice() + ballPrice) * 100);

        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        // Tao chuoi da ma hoa
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);

        // Tao chuoi chua ma hoa
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        // Thêm vnp_SecureHash vào URL
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData);

        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        // Tao URL Final
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return VNPayResponse.builder()
                .code("OK")
                .message("Mã thanh toán đã được tạo thành công. Bạn sẽ được chuyển đến cổng thanh toán để hoàn tất giao dịch.")
                .paymentUrl(paymentUrl).build();
    }

    @Transactional
    public PaymentResponse updatePayment(PaymentCallbackRequest request){
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_EXISTED));
        booking.setStatus(BookingStatus.BOOKED);
        bookingRepository.save(booking);

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .paymentTime(LocalDateTime.now())
                .status(PaymentStatus.PAID)
                .booking(booking)
                .build();

        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    public PaymentResponse getPaymentById(long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        return paymentMapper.toPaymentResponse(payment);
    }

//    public PaymentResponse updatePayment(long id, PaymentRequest request) {
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));
//
//        paymentMapper.updatePayment(payment, request);
//
//        if(request.getBookingId() != null){
//            bookingRepository.findById(request.getBookingId()).ifPresent(payment::setBooking);
//        }
//
//        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
//    }

    @Transactional
    public void deletePayment(long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        if(payment.getBooking() != null){
            payment.getBooking().setPayment(null);
        }

        paymentRepository.delete(payment);
    }

    public PageResponse<PaymentResponse> fetchAllPayments(int pageNo, int pageSize, String sortBy){
        pageNo = pageNo - 1;

        Pageable pageable = pageableService.createPageable(pageNo, pageSize, sortBy);

        Page<Payment> paymentPage = paymentRepository.findAll(pageable);

        List<PaymentResponse> responses =  convertListPromotionResponse(paymentPage.getContent());

        return PageResponse.<PaymentResponse>builder()
                .page(paymentPage.getNumber() + 1)
                .size(paymentPage.getSize())
                .totalPages(paymentPage.getTotalPages())
                .totalItems(paymentPage.getTotalElements())
                .items(responses)
                .build();
    }

    public List<PaymentResponse> convertListPromotionResponse(List<Payment> paymentList){
        List<PaymentResponse> paymentResponseList = new ArrayList<>();
        for(Payment payment : paymentList){
            PaymentResponse response = paymentMapper.toPaymentResponse(payment);
            paymentResponseList.add(response);
        }
        return paymentResponseList;
    }
}
