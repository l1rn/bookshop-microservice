package com.l1rn.user_service.services;

import com.l1rn.user_service.dto.user.CreateUser;
import com.l1rn.user_service.models.entity.user.UserEntity;
import com.l1rn.user_service.models.enums.ERole;
import com.l1rn.user_service.models.enums.EStatus;
import com.l1rn.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    public UserEntity save(CreateUser request){
        UserEntity user = UserEntity.builder()
                .id(request.getId())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(ERole.ROLE_USER)
                .status(EStatus.STATUS_CREATED)
                .build();
        return userRepository.save(user);
    }
}
