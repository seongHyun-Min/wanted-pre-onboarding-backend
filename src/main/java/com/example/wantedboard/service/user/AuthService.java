package com.example.wantedboard.service.user;

import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.dto.user.LoginUserRequestDto;
import com.example.wantedboard.exception.user.UnauthorizedUserException;
import com.example.wantedboard.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public String login(LoginUserRequestDto requestDto) {
        User findUser = userService.getUserByEmail(requestDto.getEmail());

        //비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new UnauthorizedUserException("비밀번호가 올바르지 않습니다.");
        }
        return jwtUtil.createAccessToken(String.valueOf(findUser.getId())).getTokenValue();
    }

    void validationAccessToken(String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnauthorizedUserException("인가되지 않은 access 토큰입니다.");
        }
    }


    public User findUserByToken(String accessToken) {
        if (!accessToken.isEmpty()) {
            validationAccessToken(accessToken);
        }
        Long id = Long.parseLong(jwtUtil.getPayLoad(accessToken));
        return userService.getUserById(id);

    }
}
