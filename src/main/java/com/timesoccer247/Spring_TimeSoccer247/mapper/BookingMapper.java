package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BookingRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BookingResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Booking;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toBooking(BookingRequest request);

    BookingResponse toBookingResponse(Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBooking(@MappingTarget Booking booking, BookingRequest request);
}
