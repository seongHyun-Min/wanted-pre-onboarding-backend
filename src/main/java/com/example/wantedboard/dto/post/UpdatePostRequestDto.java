package com.example.wantedboard.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePostRequestDto {
    private String title;

    private String content;
}
