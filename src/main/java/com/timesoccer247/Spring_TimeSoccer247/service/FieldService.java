package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.FieldRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.FieldResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.FieldMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FieldService {
    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;

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

    public void deleteField(long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.FIELD_NOT_EXISTED));

        fieldRepository.delete(field);
    }
}
