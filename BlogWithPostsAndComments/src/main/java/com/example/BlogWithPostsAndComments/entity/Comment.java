package com.example.BlogWithPostsAndComments.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @Column(name = "author_name")
    private String authorName;
    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;

    void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public Comment setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Comment setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }
}
