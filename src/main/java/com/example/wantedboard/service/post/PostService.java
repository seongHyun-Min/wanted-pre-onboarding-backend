package com.example.wantedboard.service.post;

import com.example.wantedboard.domain.entity.Post;
import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.domain.repository.PostRepository;
import com.example.wantedboard.dto.post.CreatePostRequestDto;
import com.example.wantedboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public Long createPost(Long userId, CreatePostRequestDto requestDto) {
        User user = userService.getUserById(userId);
        Post post = Post.builder()
                .user(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        return postRepository.save(post).getId();
    }
}
