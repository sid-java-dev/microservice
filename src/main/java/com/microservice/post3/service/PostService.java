package com.microservice.post3.service;

import com.microservice.post3.entity.Post;
import com.microservice.post3.payload.PostDto;
import com.microservice.post3.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Post savePost(Post post){
        String postId = UUID.randomUUID().toString();
        post.setPostId(postId);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    public Post getPostById(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            return post;
        }else{
            return null;
        }
    }

    public PostDto getPostWithComments(String postId){
        ArrayList comments = restTemplate.getForObject("http://COMMENT-SERVICE/api/comments/" + postId, ArrayList.class);
        Post post = postRepository.findById(postId).get();
        PostDto postDto=new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setComments(comments);
        return postDto;
    }
}
