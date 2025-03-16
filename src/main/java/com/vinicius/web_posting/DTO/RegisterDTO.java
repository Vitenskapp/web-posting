package com.vinicius.web_posting.DTO;

import com.vinicius.web_posting.Model.Enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
