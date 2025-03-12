package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.FieldResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PaymentResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import com.timesoccer247.Spring_TimeSoccer247.entity.Payment;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.FieldMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.BallRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.BookingRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.FieldRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FieldService {
    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;
    private final PageableService pageableService;
    private final BallRepository ballRepository;
    private final BookingRepository bookingRepository;

    public FieldResponse addField(FieldRequest request) {
        if(fieldRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.FIELD_EXISTED);
        }

        Field field = fieldMapper.toField(request);

        return fieldMapper.toFieldResponse(fieldRepository.save(field));
    }

    public FieldResponse getFieldById(long id){
        Field field = fieldRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.FIELD_NOT_EXISTED));

        return fieldMapper.toFieldResponse(field);
    }

    public FieldResponse updateField(long id, FieldRequest request) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.FIELD_NOT_EXISTED));

        fieldMapper.updateField(field, request);

        return fieldMapper.toFieldResponse(fieldRepository.save(field));
    }

    @Transactional
    public void deleteField(long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.FIELD_NOT_EXISTED));

        if(!CollectionUtils.isEmpty(field.getBalls())){
            field.getBalls().forEach(ball -> ball.setField(null));
        }

        if(!CollectionUtils.isEmpty(field.getBookings())){
            field.getBookings().forEach(booking -> booking.setField(null));
        }

        fieldRepository.delete(field);
    }

    public PageResponse<FieldResponse> fetchAllFields(int pageNo, int pageSize, String sortBy){
        pageNo = pageNo - 1;

        Pageable pageable = pageableService.createPageable(pageNo, pageSize, sortBy);

        Page<Field> fieldPage = fieldRepository.findAll(pageable);

        List<FieldResponse> responses =  convertListPromotionResponse(fieldPage.getContent());

        return PageResponse.<FieldResponse>builder()
                .page(fieldPage.getNumber() + 1)
                .size(fieldPage.getSize())
                .totalPages(fieldPage.getTotalPages())
                .totalItems(fieldPage.getTotalElements())
                .items(responses)
                .build();
    }

    public List<FieldResponse> convertListPromotionResponse(List<Field> fieldList){
        List<FieldResponse> fieldResponseList = new ArrayList<>();
        for(Field field : fieldList){
            FieldResponse response = fieldMapper.toFieldResponse(field);
            fieldResponseList.add(response);
        }
        return fieldResponseList;
    }
}
