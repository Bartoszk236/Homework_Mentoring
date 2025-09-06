package com.example.BlogWithPostsAndComments.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<BlogPost> blogPosts = new ArrayList<>();

    void setBlogPost(BlogPost blogPost) {
        this.blogPosts.add(blogPost);
    }

    public Author setEmail(String email) {
        this.email = email;
        return this;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }
}
