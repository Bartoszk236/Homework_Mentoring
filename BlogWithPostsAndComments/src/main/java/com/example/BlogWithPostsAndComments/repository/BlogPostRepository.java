package com.example.BlogWithPostsAndComments.repository;

import com.example.BlogWithPostsAndComments.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    @Query("SELECT p FROM BlogPost p JOIN FETCH p.comments WHERE p.id=:id")
    Optional<BlogPost> findByIdWithComments(@Param("id") Long id);
}
