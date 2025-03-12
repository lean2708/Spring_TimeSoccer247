package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.PermissionRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PermissionResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Permission;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.PermissionMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.PermissionRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final RoleRepository roleRepository;
    private final PageableService pageableService;

//    public PermissionResponse create(PermissionRequest request){
//        if(permissionRepository.existsByName(request.getName())){
//            throw new AppException(ErrorCode.PERMISSION_EXISTED);
//        }
//
//        Permission permission = permissionMapper.toPermission(request);
//
//        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
//    }

    public PermissionResponse fetchPermissionById(long id){
        Permission permissionDB = permissionRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        return permissionMapper.toPermissionResponse(permissionDB);
    }

    public PageResponse<PermissionResponse> fetchAllPermissions(int pageNo, int pageSize, String sortBy){
        pageNo = pageNo - 1;

        Pageable pageable = pageableService.createPageable(pageNo, pageSize, sortBy);

        Page<Permission> permissionPage = permissionRepository.findAll(pageable);

        List<PermissionResponse> permissionResponses = new ArrayList<>();
        for(Permission permission : permissionPage.getContent()){
            PermissionResponse response = permissionMapper.toPermissionResponse(permission);
            permissionResponses.add(response);
        }

        return PageResponse.<PermissionResponse>builder()
                .page(permissionPage.getNumber() + 1)
                .size(permissionPage.getSize())
                .totalPages(permissionPage.getTotalPages())
                .totalItems(permissionPage.getTotalElements())
                .items(permissionResponses)
                .build();
    }


//    public PermissionResponse update(long id, PermissionRequest request){
//        Permission permissionDB = permissionRepository.findById(id).
//                orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
//
//        permissionMapper.updatePermission(permissionDB, request);
//
//        return permissionMapper.toPermissionResponse(permissionRepository.save(permissionDB));
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    public void delete(long id){
//        Permission permissionDB = permissionRepository.findById(id).
//                orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
//
//        permissionDB.getRoles().forEach(role -> {
//            role.getPermissions().remove(permissionDB);
//            roleRepository.save(role);
//        });
//
//        permissionRepository.delete(permissionDB);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    public void deletePermissions(List<Long> ids){
//        List<Permission> permissionList = permissionRepository.findAllByIdIn(ids);
//        if(permissionList.isEmpty()){
//            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
//        }
//        for(Permission permission : permissionList){
//            permission.getRoles().forEach(role -> {
//                role.getPermissions().remove(permission);
//                roleRepository.save(role);
//            });
//        }
//
//        permissionRepository.deleteAllInBatch(permissionList);
//    }
}
