package com.example.wantedboard.dto.post;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CreatePostRequestDto {
    @NotBlank(message = "제목을 공백일 수 없습니다")
    private String title;

    @NotBlank(message = "본문은 공백일 수 없습니다.")
    private String content;
}

