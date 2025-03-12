package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.basic.PermissionBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.PermissionRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PermissionResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Permission;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    List<PermissionBasic> toPermissionBasic(List<Permission> permissions);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}
