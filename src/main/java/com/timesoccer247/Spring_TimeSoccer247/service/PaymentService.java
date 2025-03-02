package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.PaymentRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.FieldResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PaymentResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import com.timesoccer247.Spring_TimeSoccer247.entity.Payment;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.FieldMapper;
import com.timesoccer247.Spring_TimeSoccer247.mapper.PaymentMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.FieldRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    public PaymentResponse addPayment(PaymentRequest request) {

        Payment payment = paymentMapper.toPayment(request);

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

        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    public void deletePayment(long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        paymentRepository.delete(payment);
    }
}
