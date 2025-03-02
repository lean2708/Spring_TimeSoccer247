package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.PromotionRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PromotionResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Promotion;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.PromotionMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public PromotionResponse addPromotion(PromotionRequest request) {

        Promotion promotion = promotionMapper.toPromotion(request);

        return promotionMapper.toPromotionResponse(promotionRepository.save(promotion));
    }

    public PromotionResponse getPromotionById(long id){
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PROMOTION_NOT_EXISTED));

        return promotionMapper.toPromotionResponse(promotion);
    }

    public PromotionResponse updatePromotion(long id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PROMOTION_NOT_EXISTED));

        promotionMapper.updatePromotion(promotion, request);

        return promotionMapper.toPromotionResponse(promotionRepository.save(promotion));
    }

    public void deletePromotion(long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PROMOTION_NOT_EXISTED));

        promotionRepository.delete(promotion);
    }
}
