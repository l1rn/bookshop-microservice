//package com.l1rn.user_service.config;
//
//import com.l1rn.user_service.models.entity.user.Status;
//import com.l1rn.user_service.models.entity.user.UserEntity;
//import com.l1rn.user_service.models.enums.ERole;
//import com.l1rn.user_service.repository.RoleRepository;
//import com.l1rn.user_service.repository.UserRepository;
//import com.l1rn.user_service.services.impl.UserDetailsServiceImpl;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//public class DatabaseInitialization {
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    UserDetailsServiceImpl userDetailsService;
//    private void createUser(String username, String email,
//                            String password, ERole role){
//        if(!userRepository.existsByUsername(username)){
//            UserEntity user = UserEntity.builder()
//                    .username(username)
//                    .email(email)
//                    .roles(Set.of(
//                            roleRepository.getRoleByName(role)
//                                    .orElseThrow(() -> new RuntimeException("Такой роли не существует")))
//                    )
//                    .password(password)
//                    .build();
//
//            userRepository.save(user);
//        }
//    }
//    @EventListener(ApplicationReadyEvent.class)
//    public void initFirstUser(){
//        createUser("user",
//                "user@gmail.com",
//                "user",
//                ERole.ROLE_USER);
//    }
//}
