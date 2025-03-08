package com.vinicius.web_posting.DTO;

import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Model.User;
import jakarta.persistence.Entity;

import java.util.List;

public record UserDTO(Long id, String name, List<PostDTO> posts) {

    public UserDTO(User user) {

        this(user.getId(), user.getName(), user.getPosts().stream().map(PostDTO::new).toList());

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
