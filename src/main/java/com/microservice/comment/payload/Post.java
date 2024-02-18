package com.microservice.comment.payload;

import lombok.Data;

@Data
public class Post {
    private String id;
    private String title;
    private String description;
    private String content;
}
