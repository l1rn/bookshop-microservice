package com.l1rn.user_service.services;

import com.l1rn.user_service.dto.user.CreateUser;
import com.l1rn.user_service.models.entity.Role;
import com.l1rn.user_service.models.entity.Status;
import com.l1rn.user_service.models.entity.UserEntity;
import com.l1rn.user_service.models.enums.ERole;
import com.l1rn.user_service.models.enums.EStatus;
import com.l1rn.user_service.repository.RoleRepository;
import com.l1rn.user_service.repository.StatusRepository;
import com.l1rn.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    public UserEntity save(CreateUser request){
        UserEntity user = UserEntity.builder()
                .id(request.getId())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(Set.of(roleRepository.getRoleByName(ERole.ROLE_USER)))
                .statuses(Set.of(statusRepository.findStatusByName(EStatus.STATUS_CREATED)))
                .build();
        return userRepository.insert(user);
    }
}
