package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BallRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BallResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PromotionResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.BallService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class BallController {
    
    private final BallService ballService;
    
    @PostMapping("/balls")
    public ApiResponse<BallResponse> addBall(@Valid @RequestBody BallRequest request){
        return ApiResponse.<BallResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(ballService.addBall(request))
                .message("Create Ball")
                .build();
    }

    @GetMapping("/balls/{id}")
    public ApiResponse<BallResponse> getBallById(@PathVariable long id){
        return ApiResponse.<BallResponse>builder()
                .code(HttpStatus.OK.value())
                .result(ballService.getBallById(id))
                .message("Fetch Ball By Id")
                .build();
    }

    @PutMapping("/balls/{id}")
    public ApiResponse<BallResponse> updateBall(@PathVariable long id, @RequestBody BallRequest request){
        return ApiResponse.<BallResponse>builder()
                .code(HttpStatus.OK.value())
                .result(ballService.updateBall(id, request))
                .message("Update Ball")
                .build();
    }

    @DeleteMapping("/balls/{id}")
    public ApiResponse<Void> deleteBall(@PathVariable long id){
        ballService.deleteBall(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .result(null)
                .message("Delete Ball")
                .build();
    }
    @GetMapping("/balls")
    public ApiResponse<PageResponse<BallResponse>> fetchAll(@Min(value = 1, message = "pageNo phải lớn hơn 0")
                                                                 @RequestParam(defaultValue = "1") int pageNo,
                                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                                 @Pattern(regexp = "^(\\w+?)(-)(asc|desc)$", message = "Định dạng của sortBy phải là: field-asc hoặc field-desc")
                                                                 @RequestParam(required = false) String sortBy){
        return ApiResponse.<PageResponse<BallResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(ballService.fetchAllBalls(pageNo, pageSize, sortBy))
                .message("Fetch All Balls With Pagination")
                .build();
    }
}
