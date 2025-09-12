package com.example.BlogWithPostsAndComments.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog_posts")
@Getter
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_post_id")
    private Long id;

    @Setter
    @Column(name = "title")
    private String title;

    @Setter
    @Column(name = "content")
    private String content;

    @Setter
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @OneToMany(mappedBy = "blogPost",
            orphanRemoval = true,
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBlogPost(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setBlogPost(null);
    }

    public void setAuthor(Author author) {
        this.author = author;
        author.setBlogPost(this);
    }
}
