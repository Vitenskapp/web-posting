package com.vinicius.web_posting.Repository;

import com.vinicius.web_posting.Model.Like;
import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findByPost(Post post);
}
