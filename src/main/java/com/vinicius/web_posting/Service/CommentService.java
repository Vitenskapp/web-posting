package com.vinicius.web_posting.Service;

import com.vinicius.web_posting.DTO.CommentDTO;
import com.vinicius.web_posting.DTO.PostDTO;
import com.vinicius.web_posting.Model.Comment;
import com.vinicius.web_posting.Model.Post;
import com.vinicius.web_posting.Model.User;
import com.vinicius.web_posting.Repository.CommentRepository;
import com.vinicius.web_posting.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public List<CommentDTO> getAllComments() {

        return commentRepository.findAll().stream().map(CommentDTO::new).toList();

    }

    public Optional<Comment> getCommentById(Long id) {

        return commentRepository.findById(id);

    }

    public Comment createComment(Long id, Comment comment) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post Not Found"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public void deleteCommentById(Long id) {

        commentRepository.deleteById(id);

    }

    public List<CommentDTO> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId).stream().map(CommentDTO::new).toList();
    }

}
