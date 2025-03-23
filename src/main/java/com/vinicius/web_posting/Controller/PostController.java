package com.vinicius.web_posting.Controller;

import com.vinicius.web_posting.DTO.LikeDTO;
import com.vinicius.web_posting.DTO.PostDTO;
import com.vinicius.web_posting.Model.Like;
import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Model.User;
import com.vinicius.web_posting.Repository.PostRepository;
import com.vinicius.web_posting.Repository.UserRepository;
import com.vinicius.web_posting.Service.LikeService;
import com.vinicius.web_posting.Service.PostService;
import com.vinicius.web_posting.infra.security.JwtUtil;
import com.vinicius.web_posting.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.antlr.v4.runtime.Token;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeService likeService;

    @GetMapping
    private ResponseEntity<List<PostDTO>> getPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    private ResponseEntity<PostDTO> getPostsById(@PathVariable Long id) {

        PostDTO postDTO = new PostDTO(postService.getPostById(id).orElseThrow(() -> new RuntimeException("Post not found")));

        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<LikeDTO>> getLikesFromPost(@PathVariable Long id) {

        return ResponseEntity.ok(likeService.findLikesByPost(id).stream().map(LikeDTO::new).toList());
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, HttpServletRequest request) {

        User authenticatedUser = userRepository.findByName(tokenService.validateToken(jwtUtil.recoverToken(request))).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        post.setAuthor(authenticatedUser);

        return ResponseEntity.ok(postService.createPost(post));

    }

    @PostMapping("/{id}/like")
    public ResponseEntity<LikeDTO> likePost(@PathVariable Long id, HttpServletRequest request) {

        User authenticatedUser = userRepository.findByName(tokenService.validateToken(jwtUtil.recoverToken(request))).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ResponseEntity.ok(likeService.toggleLike(authenticatedUser.getId(), id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "id") Long id, HttpServletRequest request) {

        Post post = postService.getPostById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        User authenticatedUser = userRepository.findByName(tokenService.validateToken(jwtUtil.recoverToken(request))).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!post.getAuthor().getId().equals(authenticatedUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You not have permission to delete this post");
        }

        postService.deletePostById(id);

        return ResponseEntity.noContent().build();
    }

}
