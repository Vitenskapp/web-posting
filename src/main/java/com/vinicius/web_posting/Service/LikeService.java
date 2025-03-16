package com.vinicius.web_posting.Service;

import com.vinicius.web_posting.DTO.LikeDTO;
import com.vinicius.web_posting.Model.Like;
import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Model.User;
import com.vinicius.web_posting.Repository.LikeRepository;
import com.vinicius.web_posting.Repository.PostRepository;
import com.vinicius.web_posting.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public LikeDTO toggleLike(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);

        if(existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return null;
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);
        return new LikeDTO(like);
    }

    public Optional<Like> findLikeByUser(Long postId, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        return likeRepository.findByUserAndPost(user, post);
    }

}
