package com.example.wantedboard.domain.entity;

import com.example.wantedboard.dto.post.UpdatePostRequestDto;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "posts")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(UpdatePostRequestDto requestDto) {
        this.title = requestDto.getTitle() != null ? requestDto.getTitle() : this.title;
        this.content = requestDto.getContent() != null ? requestDto.getContent() : this.content;
    }
}
