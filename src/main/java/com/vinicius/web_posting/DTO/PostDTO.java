package com.vinicius.web_posting.DTO;

import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Model.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

public record PostDTO(Long id, String author, String content, ZonedDateTime createdAt, Integer likeCount) {
    public PostDTO(Post post) {
        this(post.getId(),
                Optional.ofNullable(post.getAuthor())
                        .map(User::getName)
                        .orElse("Desconhecido"),
                post.getContent(),
                post.getCreatedAt(),
                post.getLikes().size()
        );
    }
}
