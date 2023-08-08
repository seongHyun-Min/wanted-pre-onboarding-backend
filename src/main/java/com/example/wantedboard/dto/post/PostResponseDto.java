package com.example.wantedboard.dto.post;

import com.example.wantedboard.domain.entity.Post;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    private String title;

    private String content;

    private String email;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .email(post.getUser().getEmail())
                .build();
    }
}
