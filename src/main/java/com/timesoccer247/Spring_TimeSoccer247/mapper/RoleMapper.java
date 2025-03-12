package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.basic.RoleBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.RoleRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.RoleResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Role;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    Set<RoleBasic> toRoleBasics(Set<Role> roles);

    @Mapping(target = "permissions", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRole(@MappingTarget Role role, RoleRequest request);
}
