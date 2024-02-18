package com.microservice.comment3.servcie;

import com.microservice.comment3.entity.Comment;
import com.microservice.comment3.payload.Post;
import com.microservice.comment3.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Comment saveComment(Comment comment) {
        Post post = restTemplate.getForObject("http://POST-SERVICE/api/posts/" + comment.getPostId(), Post.class);
        if (post != null) {
            String commentId = UUID.randomUUID().toString();
            comment.setCommentId(commentId);
            Comment savedComment = commentRepository.save(comment);
            return savedComment;
        }else{
            return null;
        }
    }

    public List<Comment> getAllCommentsByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments;
    }
}
