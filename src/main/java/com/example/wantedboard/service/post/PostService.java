package com.example.wantedboard.service.post;

import com.example.wantedboard.domain.entity.Post;
import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.domain.repository.PostRepository;
import com.example.wantedboard.dto.post.CreatePostRequestDto;
import com.example.wantedboard.dto.post.PostResponseDto;
import com.example.wantedboard.exception.post.NotFoundPostException;
import com.example.wantedboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<PostResponseDto> findPostsPaged(Pageable pageable) {
        Page<Post> allPage = postRepository.findAll(pageable);
        return allPage.getContent().stream()
                .map(PostResponseDto::from)
                .toList();
    }
    @Transactional(readOnly = true)
    public PostResponseDto findPost(Long postId){
        Post findPost = getPost(postId);
        return PostResponseDto.from(findPost);
    }
    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("아이디와 일치하는 게시글을 찾을 수 없습니다"));
    }
}
