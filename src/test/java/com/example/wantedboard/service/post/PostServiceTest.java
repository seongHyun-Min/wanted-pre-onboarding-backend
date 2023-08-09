package com.example.wantedboard.service.post;

import com.example.wantedboard.domain.entity.Post;
import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.domain.repository.PostRepository;
import com.example.wantedboard.dto.post.CreatePostRequestDto;
import com.example.wantedboard.dto.post.PostResponseDto;
import com.example.wantedboard.dto.post.UpdatePostRequestDto;
import com.example.wantedboard.exception.post.NotFoundPostException;
import com.example.wantedboard.exception.user.UnauthorizedUserException;
import com.example.wantedboard.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PostService 단위 테스트")
public class PostServiceTest {

    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository, userService);
    }

    @DisplayName("포스트 생성")
    @Test
    void createPost_ValidData_ReturnsPostId() {
        Long userId = 1L;
        CreatePostRequestDto requestDto = CreatePostRequestDto.builder()
                .title("Test Title")
                .content("Test Content")
                .build();

        User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        Post createdPost = Post.builder()
                .id(1L)
                .user(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        when(userService.getUserById(userId)).thenReturn(user);
        when(postRepository.save(any(Post.class))).thenReturn(createdPost);

        Long postId = postService.createPost(userId, requestDto);

        assertEquals(1L, postId);
        verify(userService, times(1)).getUserById(userId);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @DisplayName("페이징된 포스트 조회")
    @Test
    void findPostsPaged_ReturnsListOfPostResponseDto() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, " Title 1", "Content 1", user));
        posts.add(new Post(2L, "Title 2", "Content 2", user));
        Page<Post> postPage = mock(Page.class);


        when(postRepository.findAll(any(Pageable.class))).thenReturn(postPage);
        when(postPage.getContent()).thenReturn(posts);

        List<PostResponseDto> postResponseDtoList = postService.findPostsPaged(Pageable.unpaged());

        assertEquals(2, postResponseDtoList.size());
        verify(postRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("포스트 조회 - 존재하는 아이디")
    @Test
    void findPost_ExistingPostId_ReturnsPostResponseDto() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        Long postId = 1L;
        Post post = new Post(postId, "Test Title", "Test Content", user);

        when(postRepository.findByIdWithUser(postId)).thenReturn(Optional.of(post));

        PostResponseDto postResponseDto = postService.findPost(postId);

        assertNotNull(postResponseDto);
        assertEquals("Test Title", postResponseDto.getTitle());
        assertEquals("Test Content", postResponseDto.getContent());
    }

    @DisplayName("포스트 조회 - 존재하지 않는 아이디")
    @Test
    void findPost_NonExistingPostId_ThrowsNotFoundPostException() {
        Long postId = 1L;

        when(postRepository.findByIdWithUser(postId)).thenReturn(Optional.empty());

        assertThrows(NotFoundPostException.class, () -> postService.findPost(postId));
        verify(postRepository, times(1)).findByIdWithUser(postId);
    }

    @DisplayName("포스트 수정")
    @Test
    void updatePost_ValidData_ReturnsUpdatedPostResponseDto() {
        Long userId = 1L;
        Long postId = 1L;
        UpdatePostRequestDto requestDto = UpdatePostRequestDto.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build();

        User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        Post originalPost = Post.builder()
                .id(postId)
                .user(user)
                .title("Original Title")
                .content("Original Content")
                .build();

        when(userService.getUserById(userId)).thenReturn(user);
        when(postRepository.findByIdWithUser(postId)).thenReturn(Optional.of(originalPost));

        PostResponseDto updatedPostResponseDto = postService.updatePost(userId, postId, requestDto);

        assertNotNull(updatedPostResponseDto);
        assertEquals("Updated Title", updatedPostResponseDto.getTitle());
        assertEquals("Updated Content", updatedPostResponseDto.getContent());
    }

    @DisplayName("권한 없는 포스트 수정")
    @Test
    void updatePost_UnauthorizedUser_ThrowsUnauthorizedUserException() {
        Long userId = 1L;
        Long postId = 1L;
        UpdatePostRequestDto requestDto = UpdatePostRequestDto.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build();

        User postOwner = User.builder()
                .id(2L)
                .email("owner@example.com")
                .password("encodedPassword")
                .build();

        Post originalPost = Post.builder()
                .id(postId)
                .user(postOwner)
                .title("Original Title")
                .content("Original Content")
                .build();

        when(userService.getUserById(userId)).thenReturn(User.builder().id(userId).build());
        when(postRepository.findByIdWithUser(postId)).thenReturn(Optional.of(originalPost));

        assertThrows(UnauthorizedUserException.class,
                () -> postService.updatePost(userId, postId, requestDto));

    }

    @DisplayName("포스트 삭제")
    @Test
    void deletePost_ValidData_DeletesPost() {
        Long userId = 1L;
        Long postId = 1L;
        Post postToDelete = Post.builder()
                .id(postId)
                .user(User.builder().id(userId).build())
                .title("Title")
                .content("Content")
                .build();

        when(userService.getUserById(userId)).thenReturn(User.builder().id(userId).build());
        when(postRepository.findByIdWithUser(postId)).thenReturn(Optional.of(postToDelete));

        assertDoesNotThrow(() -> postService.deletePost(userId, postId));
    }

    @DisplayName("권한 없는 포스트 삭제")
    @Test
    void deletePost_UnauthorizedUser_ThrowsUnauthorizedUserException() {
        Long userId = 1L;
        Long postId = 1L;

        User postOwner = User.builder()
                .id(2L)
                .email("owner@example.com")
                .password("encodedPassword")
                .build();

        Post postToDelete = Post.builder()
                .id(postId)
                .user(postOwner)
                .title("Title")
                .content("Content")
                .build();

        when(userService.getUserById(userId)).thenReturn(User.builder().id(userId).build());
        when(postRepository.findByIdWithUser(postId)).thenReturn(Optional.of(postToDelete));

        assertThrows(UnauthorizedUserException.class,
                () -> postService.deletePost(userId, postId));

    }
}
