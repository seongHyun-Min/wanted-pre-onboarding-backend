package com.example.wantedboard.domain.repository;

import com.example.wantedboard.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
