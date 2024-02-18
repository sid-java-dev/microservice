package com.microservice.post3.controller;

import com.microservice.post3.entity.Post;
import com.microservice.post3.payload.PostDto;
import com.microservice.post3.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity<Post>savePost(@RequestBody Post post){
        Post newPost = postService.savePost(post);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable String postId){
        Post post = postService.getPostById(postId);
        return post;
    }
@GetMapping("/{postId}/comments")
@CircuitBreaker(name = "commentBreaker",fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
        PostDto postDto = postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    public ResponseEntity<PostDto> commentFallback(String postId,Exception ex){
        System.out.println("Fallback are executed because server is down:"+ex.getMessage());
        ex.printStackTrace();
        PostDto dto=new PostDto();
        dto.setPostId("1234");
        dto.setTitle("service down");
        dto.setDescription("service down");
        dto.setContent("service down");
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }
}
