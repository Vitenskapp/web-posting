package com.vinicius.web_posting.Service;

import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {

        return postRepository.findAll();

    }

    public Optional<Post> getPostById(Long id) {

        return postRepository.findById(id);

    }

    public Post createPost(Post post) {

        return postRepository.save(post);

    }

    public void deletePost(Long id) {

        postRepository.deleteById(id);

    }

}
