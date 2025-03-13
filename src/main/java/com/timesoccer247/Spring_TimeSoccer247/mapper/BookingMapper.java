package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.basic.BookingBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.basic.PermissionBasic;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.BookingRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BookingResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Booking;
import com.timesoccer247.Spring_TimeSoccer247.entity.Permission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toBooking(BookingRequest request);

    @Mapping(source = "balls", target = "balls")
    BookingResponse toBookingResponse(Booking booking);

    BookingBasic toBookingBasic(Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBooking(@MappingTarget Booking booking, BookingRequest request);
}
