package com.example.BlogWithPostsAndComments.service;

import com.example.BlogWithPostsAndComments.entity.Author;
import com.example.BlogWithPostsAndComments.entity.BlogPost;
import com.example.BlogWithPostsAndComments.entity.Comment;
import com.example.BlogWithPostsAndComments.repository.AuthorRepository;
import com.example.BlogWithPostsAndComments.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogPostRepository blogPostRepository;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public BlogPost addCommentToPost(Long postId, String content, String authorName) {
        BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("Post with id: " + postId + " does not exist")
        );

        Comment comment = new Comment();
        comment.setAuthorName(authorName);
        comment.setContent(content);

        blogPost.addComment(comment);

        return blogPostRepository.save(blogPost);
    }

    @Override
    public BlogPost createPost(Long authorId, String title, String content) {
        Author author = authorRepository.findById(authorId).orElseThrow(
                () -> new IllegalArgumentException("Author with id: " + authorId + " not found")
        );

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(title);
        blogPost.setContent(content);
        blogPost.setAuthor(author);

        return blogPostRepository.save(blogPost);
    }

    @Override
    public BlogPost getPostWithComments(Long postId) {
        return blogPostRepository.findByIdWithComments(postId).orElseThrow(
                () -> new IllegalArgumentException("Post with id: " + postId + " does not exist")
        );
    }

    @Override
    public void deletePost(Long postId) {
        blogPostRepository.deleteById(postId);
    }
}
