package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.RoleRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.ApiResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.RoleResponse;
import com.timesoccer247.Spring_TimeSoccer247.service.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/roles")
    public ApiResponse<RoleResponse> create(@Valid @RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create Role")
                .result(roleService.create(request))
                .build();
    }

    @GetMapping("/roles/{id}")
    public ApiResponse<RoleResponse> fetchRoleById(@Min(value = 1, message = "ID phải lớn hơn hoặc bằng 1")
                                                   @PathVariable long id){
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Fetch Role By Id")
                .result(roleService.fetchRoleById(id))
                .build();
    }

    @GetMapping("/roles")
    public ApiResponse<PageResponse<RoleResponse>> fetchAll(@Min(value = 1, message = "pageNo phải lớn hơn 0")
                                                            @RequestParam(defaultValue = "1") int pageNo,
                                                            @RequestParam(defaultValue = "10") int pageSize,
                                                            @Pattern(regexp = "^(\\w+?)(-)(asc|desc)$", message = "Định dạng của sortBy phải là: field-asc hoặc field-desc")
                                                            @RequestParam(required = false) String sortBy){
        return ApiResponse.<PageResponse<RoleResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(roleService.fetchAllRoles(pageNo, pageSize, sortBy))
                .message("Fetch All Roles With Pagination")
                .build();
    }

    @PutMapping("/roles/{id}")
    public ApiResponse<RoleResponse> update(@Min(value = 1, message = "ID phải lớn hơn hoặc bằng 1")
                                            @PathVariable long id, @RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Update Role By Id")
                .result(roleService.update(id, request))
                .build();
    }

    @DeleteMapping("/roles/{id}")
    public ApiResponse<Void> delete(@Min(value = 1, message = "ID phải lớn hơn hoặc bằng 1")
                                    @PathVariable long id){
        roleService.delete(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Delete Role By Id")
                .result(null)
                .build();
    }

    @DeleteMapping("/roles")
    public ApiResponse<Void> deleteRoles(@Valid @RequestBody @NotEmpty(message = "Danh sách ID không được để trống!")
                                             Set<@Min(value = 1, message = "ID phải lớn hơn 0")Long> ids){
        roleService.deleteRoles(ids);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Delete Roles")
                .result(null)
                .build();
    }
}
