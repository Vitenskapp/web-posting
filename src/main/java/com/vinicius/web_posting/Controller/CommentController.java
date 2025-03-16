package com.vinicius.web_posting.Controller;

import com.vinicius.web_posting.DTO.CommentDTO;
import com.vinicius.web_posting.Model.Comment;
import com.vinicius.web_posting.Model.User;
import com.vinicius.web_posting.Repository.UserRepository;
import com.vinicius.web_posting.Service.CommentService;
import com.vinicius.web_posting.infra.security.JwtUtil;
import com.vinicius.web_posting.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class CommentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody Comment comment, HttpServletRequest request) {

        User authenticatedUser = userRepository.findByName(tokenService.validateToken(jwtUtil.recoverToken(request))).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        comment.setCommentAuthor(authenticatedUser);

        Comment createdComment = commentService.createComment(postId, comment);
        return ResponseEntity.ok(new CommentDTO(createdComment));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, HttpServletRequest request) {

        User authenticatedUser = userRepository.findByName(tokenService.validateToken(jwtUtil.recoverToken(request))).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Comment comment = commentService.getCommentById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        if(!comment.getCommentAuthor().getId().equals(authenticatedUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You not have permission to delete this comment");
        }

        return ResponseEntity.noContent().build();
    }

}
