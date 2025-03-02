package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.FieldResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class FieldController {
    private final FieldService fieldService;

    @PostMapping("/fields")
    public ApiResponse<FieldResponse> addField(@RequestBody FieldRequest request){
        return ApiResponse.<FieldResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(fieldService.addField(request))
                .message("Create Field")
                .build();
    }

    @GetMapping("/fields/{id}")
    public ApiResponse<FieldResponse> getFieldById(@PathVariable long id){
        return ApiResponse.<FieldResponse>builder()
                .code(HttpStatus.OK.value())
                .result(fieldService.getFieldById(id))
                .message("Fetch Field By Id")
                .build();
    }

    @PutMapping("/fields/{id}")
    public ApiResponse<FieldResponse> updateField(@PathVariable long id, @RequestBody FieldRequest request){
        return ApiResponse.<FieldResponse>builder()
                .code(HttpStatus.OK.value())
                .result(fieldService.updateField(id, request))
                .message("Update Field")
                .build();
    }

    @DeleteMapping("/fields/{id}")
    public ApiResponse<Void> deleteField(@PathVariable long id){
        fieldService.deleteField(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .result(null)
                .message("Delete Field")
                .build();
    }
}
