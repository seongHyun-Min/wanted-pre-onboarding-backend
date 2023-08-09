package com.example.wantedboard.service.user;

import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.domain.repository.UserRepository;
import com.example.wantedboard.dto.user.CreateUserRequestDto;
import com.example.wantedboard.exception.user.DuplicateUserException;
import com.example.wantedboard.exception.user.NotFoundUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("UserService 단위테스트")
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Mockito의 Mock 객체 초기화
        MockitoAnnotations.openMocks(this);
        // UserService 객체 생성 및 의존성 주입
        userService = new UserService(userRepository, passwordEncoder);
    }

    @DisplayName("유저 생성 - 유효한 요청")
    @Test
    void createUser_ValidRequest_ReturnsUserId() {
        //given
        CreateUserRequestDto requestDto = CreateUserRequestDto.builder()
                .email("test@example.com")
                .password("password")
                .build();

        // UserRepository.findByEmail() 메소드가 빈 결과를 반환하도록 설정
        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
        // PasswordEncoder.encode() 메소드가 암호를 인코딩한 값을 반환하도록 설정
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");
        // UserRepository.save() 메소드가 새로운 User 객체를 반환하도록 설정
        when(userRepository.save(any(User.class))).thenReturn(
                User.builder()
                        .id(1L)
                        .email(requestDto.getEmail())
                        .password("encodedPassword")
                        .build()
        );

        // when
        Long userId = userService.createUser(requestDto);

        // then
        assertEquals(1L, userId);
        verify(userRepository, times(1)).findByEmail(requestDto.getEmail());
        verify(passwordEncoder, times(1)).encode(requestDto.getPassword());
        // UserRepository.save() 메소드가 호출되었는지 확인
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("유저 생성 - 중복된 이메일")
    @Test
    void createUser_DuplicateEmail_ThrowsDuplicateUserException() {
        //given
        CreateUserRequestDto requestDto = CreateUserRequestDto.builder()
                .email("test@example.com")
                .password("password")
                .build();

        // UserRepository.findByEmail() 메소드가 중복된 이메일을 가진 유저를 반환하도록 설정
        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.of(User.builder().build()));

        // when / then
        // 중복된 이메일로 유저 생성을 시도하면 DuplicateUserException이 발생하는지 확인
        assertThrows(DuplicateUserException.class, () -> userService.createUser(requestDto));
        verify(userRepository, times(1)).findByEmail(requestDto.getEmail());
        verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

    @DisplayName("이메일로 유저 조회 - 존재하는 이메일")
    @Test
    void getUserByEmail_ExistingEmail_ReturnsUser() {
        //given
        String email = "test@example.com";
        User user = User.builder()
                .id(1L)
                .email(email)
                .password("encodedPassword")
                .build();

        // UserRepository.findByEmail() 메소드가 존재하는 유저 객체를 반환하도록 설정
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        User foundUser = userService.getUserByEmail(email);

        // then
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @DisplayName("이메일로 유저 조회 - 존재하지 않는 이메일")
    @Test
    void getUserByEmail_NonExistingEmail_ThrowsNotFoundUserException() {
        //given
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when / then
        assertThrows(NotFoundUserException.class, () -> userService.getUserByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @DisplayName("아이디로 유저 조회 - 존재하는 아이디")
    @Test
    void getUserById_ExistingUserId_ReturnsUser() {
        //given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        // UserRepository.findById() 메소드가 존재하는 유저 객체를 반환하도록 설정
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User foundUser = userService.getUserById(userId);

        // then
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @DisplayName("아이디로 유저 조회 - 존재하지 않는 아이디")
    @Test
    void getUserById_NonExistingUserId_ThrowsNotFoundUserException() {
        //given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when / then
        assertThrows(NotFoundUserException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}
