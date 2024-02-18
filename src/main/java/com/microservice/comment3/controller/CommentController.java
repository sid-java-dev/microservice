package com.microservice.comment3.controller;

import com.microservice.comment3.entity.Comment;
import com.microservice.comment3.servcie.CommentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping
    @CircuitBreaker(name = "postBreaker",fallbackMethod = "postFallback")
    public ResponseEntity<Comment>saveComment(@RequestBody Comment comment){
        Comment newComment = commentService.saveComment(comment);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
    public ResponseEntity<Comment> postFallback(Comment comment,Exception ex){
        System.out.println("Fallback are executed because server is down:"+ex.getMessage());
        ex.printStackTrace();
        Comment c=new Comment();
        c.setCommentId("1234");
        c.setName("service down");
        c.setEmail("service down");
        c.setPostId("service down");
        c.setBody("service down");
        return new ResponseEntity<>(c,HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{postId}")
    public List<Comment> getAllCommentsByPostId(@PathVariable String postId){
        List<Comment> allCommentsByPostId = commentService.getAllCommentsByPostId(postId);
        return allCommentsByPostId;
    }
}
