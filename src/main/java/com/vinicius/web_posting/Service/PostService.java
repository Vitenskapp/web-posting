package com.vinicius.web_posting.Service;

import com.vinicius.web_posting.DTO.PostDTO;
import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostDTO> getAllPosts() {

        return postRepository.findAll().stream().map(PostDTO::new).toList();

    }

    public Optional<PostDTO> getPostById(Long id) {

        return postRepository.findById(id).map(PostDTO::new);

    }

    public Post createPost(Post post) {

        return postRepository.save(post);

    }

    public void deletePostById(Long id) {

        postRepository.deleteById(id);

    }

}
