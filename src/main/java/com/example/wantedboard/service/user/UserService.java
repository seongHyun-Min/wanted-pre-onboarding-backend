package com.example.wantedboard.service.user;

import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.domain.repository.UserRepository;
import com.example.wantedboard.dto.user.CreateUserRequestDto;
import com.example.wantedboard.exception.user.DuplicateUserException;
import com.example.wantedboard.exception.user.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createUser(CreateUserRequestDto requestDto) {
        checkExistingUser(requestDto.getEmail());
        String encryptedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encryptedPassword)
                .build();

        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundUserException("해당 이메일과 일치하는 회원정보를 찾을수 없습니다"));
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("해당 아이디와 일치하는 회원정보를 찾을수 없습니다"));
    }


    private void checkExistingUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateUserException("이미 등록된 유저(이메일)입니다.");
        }
    }
}
