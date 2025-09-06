package com.example.BlogWithPostsAndComments.service;

import com.example.BlogWithPostsAndComments.entity.BlogPost;

public interface BlogService {
    BlogPost createPost(Long authorId, String title, String content);

    BlogPost addCommentToPost(Long postId, String content, String authorName);

    BlogPost getPostWithComments(Long postId);

    void deletePost(Long postId);
}
