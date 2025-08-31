package com.example.LoggingWithLevelsJsonLogsAndMdc.repository;

import com.example.LoggingWithLevelsJsonLogsAndMdc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
