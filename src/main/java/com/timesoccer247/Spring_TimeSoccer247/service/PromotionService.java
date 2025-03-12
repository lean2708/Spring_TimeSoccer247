package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.PromotionRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PromotionResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.UserResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Promotion;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.PromotionMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.PromotionRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PageableService pageableService;

    public PromotionResponse addPromotion(PromotionRequest request) {

        Promotion promotion = promotionMapper.toPromotion(request);

        userRepository.findByEmail(authService.getCurrentUsername())
                .ifPresent(promotion::setUser);

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

        userRepository.findByEmail(authService.getCurrentUsername())
                .ifPresent(promotion::setUser);

        return promotionMapper.toPromotionResponse(promotionRepository.save(promotion));
    }

    @Transactional
    public void deletePromotion(long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PROMOTION_NOT_EXISTED));

        if(promotion.getUser() != null){
            promotion.getUser().getPromotions().remove(promotion);
        }

        promotionRepository.delete(promotion);
    }

    public PageResponse<PromotionResponse> fetchAllPromotions(int pageNo, int pageSize, String sortBy){
        pageNo = pageNo - 1;

        Pageable pageable = pageableService.createPageable(pageNo, pageSize, sortBy);

        Page<Promotion> promotionPage = promotionRepository.findAll(pageable);

        List<PromotionResponse> responses =  convertListPromotionResponse(promotionPage.getContent());

        return PageResponse.<PromotionResponse>builder()
                .page(promotionPage.getNumber() + 1)
                .size(promotionPage.getSize())
                .totalPages(promotionPage.getTotalPages())
                .totalItems(promotionPage.getTotalElements())
                .items(responses)
                .build();
    }

    public List<PromotionResponse> convertListPromotionResponse(List<Promotion> promotionList){
        List<PromotionResponse> promotionResponseList = new ArrayList<>();
        for(Promotion promotion : promotionList){
            PromotionResponse response = promotionMapper.toPromotionResponse(promotion);
            promotionResponseList.add(response);
        }
        return promotionResponseList;
    }
}
