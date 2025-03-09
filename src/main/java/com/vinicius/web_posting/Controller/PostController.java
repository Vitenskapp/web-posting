package com.vinicius.web_posting.Controller;

import com.vinicius.web_posting.DTO.PostDTO;
import com.vinicius.web_posting.Model.Like;
import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Repository.PostRepository;
import com.vinicius.web_posting.Service.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @GetMapping
    private ResponseEntity<List<PostDTO>> getPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Optional<PostDTO>> getPostsById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {

        return ResponseEntity.ok(postService.createPost(post));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

}
