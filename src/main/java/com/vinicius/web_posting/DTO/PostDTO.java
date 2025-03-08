package com.vinicius.web_posting.DTO;

import com.vinicius.web_posting.Model.Post;

import java.util.Optional;

public record PostDTO(Long id, String author, String content) {
    public PostDTO(Post post) {
        this(post.getId(), post.getAuthor().getName(), post.getContent());
    }
}
