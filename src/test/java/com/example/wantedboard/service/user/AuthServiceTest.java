package com.example.wantedboard.service.user;


import com.example.wantedboard.config.security.Token;
import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.dto.user.LoginUserRequestDto;
import com.example.wantedboard.exception.user.UnauthorizedUserException;
import com.example.wantedboard.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AuthService 단위 테스트")
public class AuthServiceTest {

    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userService, jwtUtil, passwordEncoder);
    }

    @DisplayName("로그인 - 올바른 비밀번호")
    @Test
    void login_ValidPassword_ReturnsAccessToken() {
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String accessToken = "validAccessToken";

        LoginUserRequestDto requestDto = new LoginUserRequestDto(email, password);

        User user = User.builder()
                .id(1L)
                .email(email)
                .password(encodedPassword)
                .build();

        when(userService.getUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtUtil.createAccessToken(String.valueOf(user.getId()))).thenReturn(
                Token.builder().tokenValue(accessToken).build());


        String generatedAccessToken = authService.login(requestDto);

        assertEquals(accessToken, generatedAccessToken);
        verify(userService, times(1)).getUserByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(jwtUtil, times(1)).createAccessToken(String.valueOf(user.getId()));
    }

    @DisplayName("로그인 - 올바르지 않은 비밀번호")
    @Test
    void login_InvalidPassword_ThrowsUnauthorizedUserException() {
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        LoginUserRequestDto requestDto = new LoginUserRequestDto(email, password);

        User user = User.builder()
                .id(1L)
                .email(email)
                .password(encodedPassword)
                .build();

        when(userService.getUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        assertThrows(UnauthorizedUserException.class, () -> authService.login(requestDto));
        verify(userService, times(1)).getUserByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verifyNoMoreInteractions(jwtUtil);
    }

    @DisplayName("유효한 AccessToken 검증")
    @Test
    void validationAccessToken_ValidToken_NoException() {
        String accessToken = "validAccessToken";

        when(jwtUtil.validateToken(accessToken)).thenReturn(true);

        assertDoesNotThrow(() -> authService.validationAccessToken(accessToken));
        verify(jwtUtil, times(1)).validateToken(accessToken);
    }

    @DisplayName("유효하지 않은 AccessToken 검증")
    @Test
    void validationAccessToken_InvalidToken_ThrowsUnauthorizedUserException() {
        String accessToken = "invalidAccessToken";

        when(jwtUtil.validateToken(accessToken)).thenReturn(false);

        assertThrows(UnauthorizedUserException.class, () -> authService.validationAccessToken(accessToken));
        verify(jwtUtil, times(1)).validateToken(accessToken);
    }

    @DisplayName("AccessToken으로 유저 조회")
    @Test
    void findUserByToken_ValidAccessToken_ReturnsUser() {
        String accessToken = "validAccessToken";
        Long userId = 1L;

        when(jwtUtil.validateToken(accessToken)).thenReturn(true);
        when(jwtUtil.getPayLoad(accessToken)).thenReturn(String.valueOf(userId));

        User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        when(userService.getUserById(userId)).thenReturn(user);

        User foundUser = authService.findUserByToken(accessToken);

        assertEquals(user, foundUser);
        verify(jwtUtil, times(1)).validateToken(accessToken);
        verify(jwtUtil, times(1)).getPayLoad(accessToken);
        verify(userService, times(1)).getUserById(userId);
    }

    @DisplayName("유효하지 않은 AccessToken으로 유저 조회")
    @Test
    void findUserByToken_InvalidAccessToken_ThrowsUnauthorizedUserException() {
        String accessToken = "invalidAccessToken";

        when(jwtUtil.validateToken(accessToken)).thenReturn(false);

        assertThrows(UnauthorizedUserException.class, () -> authService.findUserByToken(accessToken));
        verify(jwtUtil, times(1)).validateToken(accessToken);
        verifyNoMoreInteractions(jwtUtil, userService);
    }
}
