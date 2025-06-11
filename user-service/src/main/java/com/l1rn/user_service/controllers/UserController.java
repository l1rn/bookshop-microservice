package com.l1rn.user_service.controllers;

import com.l1rn.user_service.dto.user.CreateUser;
import com.l1rn.user_service.models.entity.user.UserEntity;
import com.l1rn.user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<UserEntity> save(@RequestBody CreateUser user){
        try {
            return ResponseEntity.ok(userService.save(user));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
