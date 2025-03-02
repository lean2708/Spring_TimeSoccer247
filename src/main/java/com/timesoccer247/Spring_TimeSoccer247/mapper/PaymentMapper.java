package com.timesoccer247.Spring_TimeSoccer247.mapper;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.request.PaymentRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.FieldResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PaymentResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import com.timesoccer247.Spring_TimeSoccer247.entity.Payment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPayment(PaymentRequest request);

    PaymentResponse toPaymentResponse(Payment payment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePayment(@MappingTarget Payment payment, PaymentRequest request);
}
