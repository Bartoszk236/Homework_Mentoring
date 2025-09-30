package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPostsTitle(String postTitle);

    List<User> findByPostsCreatedDateBetween(LocalDate start, LocalDate end);

    @Modifying
    @Transactional
    void deleteUsersByLastLoginBefore(LocalDateTime before);

    @Modifying
    @Transactional
    @Query("""
            update User u
            set u.active = false
            where u.lastLogin < :cutoffDate
            """)
    int deactivateInactiveUsers(
            @Param("cutoffDate") LocalDateTime cutoffDate);
}
