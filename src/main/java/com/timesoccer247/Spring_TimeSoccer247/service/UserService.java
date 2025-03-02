package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.UserRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.UserResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.UserMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest request) {
       if(userRepository.existsByEmail(request.getEmail())){
           throw new AppException(ErrorCode.EMAIL_EXISTED);
       }

       if(userRepository.existsByPhone(request.getPhone())){
            throw new AppException(ErrorCode.PHONE_EXISTED);
       }

       User user = userMapper.toUser(request);
       user.setPassword(passwordEncoder.encode(request.getPassword()));

       return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getUserById(long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        userRepository.delete(user);
    }
}
