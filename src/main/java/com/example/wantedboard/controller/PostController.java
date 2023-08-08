package com.example.wantedboard.controller;

import com.example.wantedboard.config.security.UserAuthentication;
import com.example.wantedboard.dto.post.CreatePostRequestDto;
import com.example.wantedboard.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
@Slf4j
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<Long> creatPost(UserAuthentication authentication,
                                          @Valid @RequestBody CreatePostRequestDto requestDto) {
        Long userId = authentication.getUserId();
        log.info(String.valueOf(userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(userId, requestDto));
    }
}
