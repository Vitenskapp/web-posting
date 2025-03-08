package com.vinicius.web_posting.DTO;

import com.vinicius.web_posting.Model.Comment;
import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Model.User;

import java.time.ZonedDateTime;
import java.util.Optional;

public record CommentDTO(Long id, String commentAuthor, String content, ZonedDateTime createdAt, Long postId) {
    public CommentDTO(Comment comment) {
        this(comment.getId(),
                comment.getCommentAuthor().getName(),
                comment.getContent(),
                comment.getCreatedAt(),
                Optional.ofNullable(comment.getPost())
                        .map(Post::getId)
                        .orElse(null));
    }
}
