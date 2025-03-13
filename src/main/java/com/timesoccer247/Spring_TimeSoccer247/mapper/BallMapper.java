package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.basic.BallBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.FieldBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.BallRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BallResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Ball;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BallMapper {
    Ball toBall(BallRequest request);

    BallResponse toBallResponse(Ball ball);

    BallBasic toBallBasic(Ball ball);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBall(@MappingTarget Ball ball, BallRequest request);
}
