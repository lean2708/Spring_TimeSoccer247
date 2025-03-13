package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.*;
import com.timesoccer247.Spring_TimeSoccer247.service.FieldService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class FieldController {
    private final FieldService fieldService;

    @PostMapping("/fields")
    public ApiResponse<FieldResponse> addField(@Valid @RequestBody FieldRequest request){
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
    public ApiResponse<FieldResponse> updateField(@PathVariable long id,@Valid @RequestBody FieldRequest request){
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

    @GetMapping("/fields")
    public ApiResponse<PageResponse<FieldResponse>> fetchAll(@Min(value = 1, message = "pageNo phải lớn hơn 0")
                                                               @RequestParam(defaultValue = "1") int pageNo,
                                                               @RequestParam(defaultValue = "10") int pageSize,
                                                               @Pattern(regexp = "^(\\w+?)(-)(asc|desc)$", message = "Định dạng của sortBy phải là: field-asc hoặc field-desc")
                                                               @RequestParam(required = false) String sortBy){
        return ApiResponse.<PageResponse<FieldResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(fieldService.fetchAllFields(pageNo, pageSize, sortBy))
                .message("Fetch All Fields With Pagination")
                .build();
    }


}
