package com.vinicius.web_posting.DTO;

import com.vinicius.web_posting.Model.Like;
import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Model.User;

public record LikeDTO(Long likeId, Long userId, Long postId) {

    public LikeDTO(Like like) {
        this(like.getId(), like.getUser().getId(), like.getPost().getId());
    }

}
