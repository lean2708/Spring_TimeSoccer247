package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.BallRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.BallResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Ball;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.BallMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.BallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BallService {
    
    private final BallRepository ballRepository;
    private final BallMapper ballMapper;

    public BallResponse addBall(BallRequest request) {

        Ball Ball = ballMapper.toBall(request);

        return ballMapper.toBallResponse(ballRepository.save(Ball));
    }

    public BallResponse getBallById(long id){
        Ball Ball = ballRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BALL_NOT_EXISTED));

        return ballMapper.toBallResponse(Ball);
    }

    public BallResponse updateBall(long id, BallRequest request) {
        Ball Ball = ballRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BALL_NOT_EXISTED));

        ballMapper.updateBall(Ball, request);

        return ballMapper.toBallResponse(ballRepository.save(Ball));
    }

    public void deleteBall(long id) {
        Ball Ball = ballRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BALL_NOT_EXISTED));

        ballRepository.delete(Ball);
    }
}
