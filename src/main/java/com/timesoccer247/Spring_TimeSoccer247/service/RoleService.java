package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.RoleRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.RoleResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Permission;
import com.timesoccer247.Spring_TimeSoccer247.entity.Role;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.RoleMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.PermissionRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.RoleRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PageableService pageableService;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public RoleResponse create(RoleRequest request){
        if(roleRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        Role role = roleMapper.toRole(request);

        if(request.getPermissions() != null && !request.getPermissions().isEmpty()){
            Set<Permission> permissions = permissionRepository.findAllByNameIn(request.getPermissions());
            role.setPermissions(permissions);
        }

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public RoleResponse fetchRoleById(long id){
        Role roleDB = roleRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        return roleMapper.toRoleResponse(roleDB);
    }


    public PageResponse<RoleResponse> fetchAllRoles(int pageNo, int pageSize, String sortBy){
        pageNo = pageNo - 1;

        Pageable pageable = pageableService.createPageable(pageNo, pageSize, sortBy);

        Page<Role> rolePage = roleRepository.findAll(pageable);

        List<RoleResponse> responses =  new ArrayList<>();
        for(Role role : rolePage.getContent()){
            responses.add(roleMapper.toRoleResponse(role));
        }

        return PageResponse.<RoleResponse>builder()
                .page(rolePage.getNumber() + 1)
                .size(rolePage.getSize())
                .totalPages(rolePage.getTotalPages())
                .totalItems(rolePage.getTotalElements())
                .items(responses)
                .build();
    }
    
    public RoleResponse update(long id, RoleRequest request){
        Role roleDB = roleRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        roleMapper.updateRole(roleDB, request);

        if(request.getPermissions() != null && !request.getPermissions().isEmpty()){
            Set<Permission> permissions = permissionRepository.findAllByNameIn(request.getPermissions());
            roleDB.setPermissions(permissions);
        }

        return roleMapper.toRoleResponse(roleRepository.save(roleDB));
    }

    @Transactional
    public void delete(long id){
        Role roleDB = roleRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        if(!CollectionUtils.isEmpty(roleDB.getUsers())){
            roleDB.getUsers().forEach(user -> user.getRoles().remove(roleDB));
        }

        if(!CollectionUtils.isEmpty(roleDB.getPermissions())){
            roleDB.getPermissions().forEach(permission -> {
                permission.getRoles().remove(roleDB);
            });
        }

        roleRepository.delete(roleDB);
    }

    public void deleteRoles(Set<Long> ids) {
        Set<Role> roleList = roleRepository.findAllByIdIn(ids);

        if(roleList.isEmpty()){
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }

        for(Role role : roleList){
            role.getUsers().forEach(user -> {
                user.getRoles().remove(role);
                userRepository.save(user);
            });
        }

        roleRepository.deleteAllInBatch(roleList);
    }
}
