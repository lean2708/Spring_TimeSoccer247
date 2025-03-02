package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.PaymentRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PaymentResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payments")
    public ApiResponse<PaymentResponse> addPayment(@RequestBody PaymentRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(paymentService.addPayment(request))
                .message("Create Payment")
                .build();
    }

    @GetMapping("/payments/{id}")
    public ApiResponse<PaymentResponse> getPaymentById(@PathVariable long id){
        return ApiResponse.<PaymentResponse>builder()
                .code(HttpStatus.OK.value())
                .result(paymentService.getPaymentById(id))
                .message("Fetch Payment By Id")
                .build();
    }

    @PutMapping("/payments/{id}")
    public ApiResponse<PaymentResponse> updatePayment(@PathVariable long id, @RequestBody PaymentRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .code(HttpStatus.OK.value())
                .result(paymentService.updatePayment(id, request))
                .message("Update Payment")
                .build();
    }

    @DeleteMapping("/payments/{id}")
    public ApiResponse<Void> deletePayment(@PathVariable long id){
        paymentService.deletePayment(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .result(null)
                .message("Delete Payment")
                .build();
    }
}
