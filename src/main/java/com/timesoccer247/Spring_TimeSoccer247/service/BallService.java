package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BallRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BallResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Ball;
import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.BallMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.BallRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.FieldRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BallService {
    
    private final BallRepository ballRepository;
    private final BallMapper ballMapper;
    private final FieldRepository fieldRepository;
    private final PageableService pageableService;

    public BallResponse addBall(BallRequest request) {

        Ball ball = ballMapper.toBall(request);

        return ballMapper.toBallResponse(ballRepository.save(ball));
    }

    public BallResponse getBallById(long id){
        Ball ball = ballRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BALL_NOT_EXISTED));

        return ballMapper.toBallResponse(ball);
    }

    public BallResponse updateBall(long id, BallRequest request) {
        Ball ball = ballRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BALL_NOT_EXISTED));

        ballMapper.updateBall(ball, request);

        return ballMapper.toBallResponse(ballRepository.save(ball));
    }

    @Transactional
    public void deleteBall(long id) {
        Ball ball = ballRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BALL_NOT_EXISTED));

        ballRepository.delete(ball);
    }

    public PageResponse<BallResponse> fetchAllBalls(int pageNo, int pageSize, String sortBy){
        pageNo = pageNo - 1;

        Pageable pageable = pageableService.createPageable(pageNo, pageSize, sortBy);

        Page<Ball> ballPage = ballRepository.findAll(pageable);

        List<BallResponse> responses =  convertListPromotionResponse(ballPage.getContent());

        return PageResponse.<BallResponse>builder()
                .page(ballPage.getNumber() + 1)
                .size(ballPage.getSize())
                .totalPages(ballPage.getTotalPages())
                .totalItems(ballPage.getTotalElements())
                .items(responses)
                .build();
    }

    public List<BallResponse> convertListPromotionResponse(List<Ball> ballList){
        List<BallResponse> ballResponseList = new ArrayList<>();
        for(Ball ball : ballList){
            BallResponse response = ballMapper.toBallResponse(ball);
            ballResponseList.add(response);
        }
        return ballResponseList;
    }
}
