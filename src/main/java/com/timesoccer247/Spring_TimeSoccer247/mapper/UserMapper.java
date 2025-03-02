package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.UserRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.UserResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest request);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserRequest request);
}
