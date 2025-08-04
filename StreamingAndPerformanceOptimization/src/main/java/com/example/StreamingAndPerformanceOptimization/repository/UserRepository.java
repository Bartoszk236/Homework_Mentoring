package com.example.StreamingAndPerformanceOptimization.repository;

import com.example.StreamingAndPerformanceOptimization.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
}
