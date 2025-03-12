package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.PaymentRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.FieldResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PaymentResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PromotionResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import com.timesoccer247.Spring_TimeSoccer247.entity.Payment;
import com.timesoccer247.Spring_TimeSoccer247.entity.Promotion;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.FieldMapper;
import com.timesoccer247.Spring_TimeSoccer247.mapper.PaymentMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.BookingRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.FieldRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PageableService pageableService;

    public PaymentResponse addPayment(PaymentRequest request) {

        Payment payment = paymentMapper.toPayment(request);

        if(request.getBookingId() != null){
            bookingRepository.findById(request.getBookingId()).ifPresent(payment::setBooking);
        }

        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    public PaymentResponse getPaymentById(long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        return paymentMapper.toPaymentResponse(payment);
    }

    public PaymentResponse updatePayment(long id, PaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        paymentMapper.updatePayment(payment, request);

        if(request.getBookingId() != null){
            bookingRepository.findById(request.getBookingId()).ifPresent(payment::setBooking);
        }

        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

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
