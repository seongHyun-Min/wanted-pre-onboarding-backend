package com.example.wantedboard.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class LoginUserRequestDto {
    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = ".*@.*", message = "이메일은 '@'를 반드시 포함해야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
    private String password;
}
