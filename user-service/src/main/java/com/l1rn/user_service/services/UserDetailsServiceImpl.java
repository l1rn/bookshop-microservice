package com.l1rn.user_service.services;

import com.l1rn.user_service.models.entity.UserEntity;
import com.l1rn.user_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        return UserDetailsImpl.build(user);
    }
}
