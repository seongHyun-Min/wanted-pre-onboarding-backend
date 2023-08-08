package com.example.wantedboard.service.user;

import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.domain.entity.repository.UserRepository;
import com.example.wantedboard.dto.user.CreateUserRequestDto;
import com.example.wantedboard.exception.user.DuplicateUserException;
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


    private void checkExistingUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateUserException("이미 존재하는 유저입니다.");
        }
    }
}
