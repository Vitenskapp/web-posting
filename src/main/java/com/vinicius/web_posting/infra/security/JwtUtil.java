package com.vinicius.web_posting.infra.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

    public String recoverToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }

        return null;

    }

}
