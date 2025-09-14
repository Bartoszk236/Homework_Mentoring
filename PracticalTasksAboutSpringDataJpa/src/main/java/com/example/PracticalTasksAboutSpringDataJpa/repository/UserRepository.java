package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPostsTitle(String postTitle);

    List<User> findByPostsCreatedDateBetween(LocalDate start, LocalDate end);
}
