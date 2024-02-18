package com.microservice.post3.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String postId;
    private String title;
    private String description;
    private String content;
    private List<Comment> comments;
}
