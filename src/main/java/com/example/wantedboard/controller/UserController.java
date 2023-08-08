package com.example.wantedboard.controller;

import com.example.wantedboard.dto.user.CreateUserRequestDto;
import com.example.wantedboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        Long userId = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
}
