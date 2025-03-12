package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.basic.UserBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.RegisterRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.UserRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.UserResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest request);

    User toUser(RegisterRequest request);

    @Mapping(source = "roles", target = "roles")
    UserResponse toUserResponse(User user);

    UserBasic toUserBasic(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserRequest request);

}
