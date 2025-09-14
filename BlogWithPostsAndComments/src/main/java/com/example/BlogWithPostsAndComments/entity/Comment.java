package com.example.BlogWithPostsAndComments.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Setter
    @Column(name = "content")
    private String content;

    @Setter
    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Setter
    @Column(name = "author_name")
    private String authorName;

    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;

    void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }
}
