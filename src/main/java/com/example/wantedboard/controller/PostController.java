package com.example.wantedboard.controller;

import com.example.wantedboard.config.security.UserAuthentication;
import com.example.wantedboard.dto.post.CreatePostRequestDto;
import com.example.wantedboard.dto.post.PostResponseDto;
import com.example.wantedboard.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findPostsPaged(Pageable pageable) {
        return ResponseEntity.ok(postService.findPostsPaged(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPost(postId));
    }
}
