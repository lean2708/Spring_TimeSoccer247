package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BallRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BallResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.BallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class BallController {
    
    private final BallService ballService;
    
    @PostMapping("/balls")
    public ApiResponse<BallResponse> addBall(@RequestBody BallRequest request){
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
}
