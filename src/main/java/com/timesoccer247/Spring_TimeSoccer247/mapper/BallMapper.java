package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BallRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BallResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Ball;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BallMapper {
    Ball toBall(BallRequest request);

    BallResponse toBallResponse(Ball ball);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBall(@MappingTarget Ball ball, BallRequest request);
}
