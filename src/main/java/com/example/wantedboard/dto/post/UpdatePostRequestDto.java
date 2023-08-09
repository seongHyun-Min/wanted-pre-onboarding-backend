package com.example.wantedboard.dto.post;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class UpdatePostRequestDto {
    private String title;

    private String content;
}
