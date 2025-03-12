package com.timesoccer247.Spring_TimeSoccer247.service;

import com.timesoccer247.Spring_TimeSoccer247.dto.request.UserRequest;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.PageResponse;
import com.timesoccer247.Spring_TimeSoccer247.dto.response.UserResponse;
import com.timesoccer247.Spring_TimeSoccer247.entity.Role;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.mapper.UserMapper;
import com.timesoccer247.Spring_TimeSoccer247.repository.BookingRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.PromotionRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.RoleRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final BookingRepository bookingRepository;
    private final PromotionRepository promotionRepository;
    private final PageableService pageableService;

    public UserResponse create(UserRequest request) {
       if(userRepository.existsByEmail(request.getEmail())){
           throw new AppException(ErrorCode.EMAIL_EXISTED);
       }

       if(userRepository.existsByPhone(request.getPhone())){
            throw new AppException(ErrorCode.PHONE_EXISTED);
       }

       User user = userMapper.toUser(request);
       user.setPassword(passwordEncoder.encode(request.getPassword()));

        if(request.getRoleIds() != null){
            Set<Role> roleList = roleRepository.findAllByIdIn(request.getRoleIds());
            user.setRoles(roleList);
        }

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

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if(request.getRoleIds() != null){
            Set<Role> roleList = roleRepository.findAllByIdIn(request.getRoleIds());
            user.setRoles(roleList);
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(user.getBookings() != null && !user.getBookings().isEmpty()){
            bookingRepository.deleteAll(user.getBookings());
        }
        if(user.getPromotions() != null && !user.getPromotions().isEmpty()){
            promotionRepository.deleteAll(user.getPromotions());
        }
        if(user.getRoles() != null && !user.getRoles().isEmpty()){
            user.getRoles().forEach(role -> role.getUsers().remove(user));
        }
        userRepository.delete(user);
    }

    public PageResponse<UserResponse> fetchAllUsers(int pageNo, int pageSize, String sortBy){
        pageNo = pageNo - 1;

        Pageable pageable = pageableService.createPageable(pageNo, pageSize, sortBy);

        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> responses =  convertListUserResponse(userPage.getContent());

        return PageResponse.<UserResponse>builder()
                .page(userPage.getNumber() + 1)
                .size(userPage.getSize())
                .totalPages(userPage.getTotalPages())
                .totalItems(userPage.getTotalElements())
                .items(responses)
                .build();
    }

    public List<UserResponse> convertListUserResponse(List<User> userList){
        List<UserResponse> userResponseList = new ArrayList<>();
        for(User user : userList){
            UserResponse response = userMapper.toUserResponse(user);
            userResponseList.add(response);
        }
        return userResponseList;
    }
}
