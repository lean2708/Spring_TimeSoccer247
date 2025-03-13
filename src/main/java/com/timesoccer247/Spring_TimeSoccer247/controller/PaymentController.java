package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.PaymentCallbackRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.*;
import com.timesoccer247.Spring_TimeSoccer247.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/payments/{bookingId}/vn-pay")
    public ApiResponse<VNPayResponse> pay(@PathVariable(value = "bookingId") Long bookingId, HttpServletRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Tạo thành công URL thanh toán VNPay",
                paymentService.createVnPayPayment(bookingId, request));
    }

    @PostMapping("/payments/vn-pay-callback")
    public ApiResponse<PaymentResponse> payCallbackHandler(@Valid @RequestBody PaymentCallbackRequest request) {
        String status = request.getResponseCode();
        if (status.equals("00")) {
            return new ApiResponse<PaymentResponse>(1000,
                    "Thanh toán thành công",
                    paymentService.updatePayment(request));
        } else {
            log.error("Thanh toán không thành công với mã phản hồi: " + status);
            return new ApiResponse<>(4000, "Thanh toán thất bại", null);
        }
    }


    @GetMapping("/payments/{id}")
    public ApiResponse<PaymentResponse> getPaymentById(@PathVariable long id){
        return ApiResponse.<PaymentResponse>builder()
                .code(HttpStatus.OK.value())
                .result(paymentService.getPaymentById(id))
                .message("Fetch Payment By Id")
                .build();
    }

//    @PutMapping("/payments/{id}")
//    public ApiResponse<PaymentResponse> updatePayment(@PathVariable long id,@Valid @RequestBody PaymentRequest request){
//        return ApiResponse.<PaymentResponse>builder()
//                .code(HttpStatus.OK.value())
//                .result(paymentService.updatePayment(id, request))
//                .message("Update Payment")
//                .build();
//    }

    @DeleteMapping("/payments/{id}")
    public ApiResponse<Void> deletePayment(@PathVariable long id){
        paymentService.deletePayment(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .result(null)
                .message("Delete Payment")
                .build();
    }

    @GetMapping("/payments")
    public ApiResponse<PageResponse<PaymentResponse>> fetchAll(@Min(value = 1, message = "pageNo phải lớn hơn 0")
                                                                 @RequestParam(defaultValue = "1") int pageNo,
                                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                                 @Pattern(regexp = "^(\\w+?)(-)(asc|desc)$", message = "Định dạng của sortBy phải là: field-asc hoặc field-desc")
                                                                 @RequestParam(required = false) String sortBy){
        return ApiResponse.<PageResponse<PaymentResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(paymentService.fetchAllPayments(pageNo, pageSize, sortBy))
                .message("Fetch All Payments With Pagination")
                .build();
    }


}
