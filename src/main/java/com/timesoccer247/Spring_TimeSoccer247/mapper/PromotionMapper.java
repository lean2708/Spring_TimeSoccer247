package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BookingRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.PromotionRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BookingResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PromotionResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Booking;
import com.timesoccer247.Spring_TimeSoccer247.entity.Promotion;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion toPromotion(PromotionRequest request);

    PromotionResponse toPromotionResponse(Promotion promotion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePromotion(@MappingTarget Promotion promotion, PromotionRequest request);
}
