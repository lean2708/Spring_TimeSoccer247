package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.PaymentRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.PromotionRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PaymentResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PromotionResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping("/promotions")
    public ApiResponse<PromotionResponse> addPromotion(@RequestBody PromotionRequest request){
        return ApiResponse.<PromotionResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(promotionService.addPromotion(request))
                .message("Create Promotion")
                .build();
    }

    @GetMapping("/promotions/{id}")
    public ApiResponse<PromotionResponse> getPromotionById(@PathVariable long id){
        return ApiResponse.<PromotionResponse>builder()
                .code(HttpStatus.OK.value())
                .result(promotionService.getPromotionById(id))
                .message("Fetch Promotion By Id")
                .build();
    }

    @PutMapping("/promotions/{id}")
    public ApiResponse<PromotionResponse> updatePromotion(@PathVariable long id, @RequestBody PromotionRequest request){
        return ApiResponse.<PromotionResponse>builder()
                .code(HttpStatus.OK.value())
                .result(promotionService.updatePromotion(id, request))
                .message("Update Promotion")
                .build();
    }

    @DeleteMapping("/promotions/{id}")
    public ApiResponse<Void> deletePromotion(@PathVariable long id){
        promotionService.deletePromotion(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .result(null)
                .message("Delete Promotion")
                .build();
    }
}
