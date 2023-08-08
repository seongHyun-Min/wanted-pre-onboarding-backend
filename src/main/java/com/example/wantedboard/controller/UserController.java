package com.example.wantedboard.controller;

import com.example.wantedboard.dto.user.CreateUserRequestDto;
import com.example.wantedboard.dto.user.LoginUserRequestDto;
import com.example.wantedboard.service.user.AuthService;
import com.example.wantedboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        Long userId = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<String> Login(@Valid @RequestBody LoginUserRequestDto requestDto){
        String jwtToken = authService.login(requestDto);
        return ResponseEntity.ok(jwtToken);
    }
}
