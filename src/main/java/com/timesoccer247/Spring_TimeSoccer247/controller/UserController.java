package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.UserRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.UserResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ApiResponse<UserResponse> create(@RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(userService.create(request))
                .message("Create User")
                .build();
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable long id){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(userService.getUserById(id))
                .message("Fetch User By Id")
                .build();
    }

    @PutMapping("/users/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable long id, @RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(userService.updateUser(id, request))
                .message("Update User")
                .build();
    }

    @DeleteMapping("/users/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .result(null)
                .message("Delete User")
                .build();
    }
}
