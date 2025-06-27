package com.example.WorkingWithHeadersAndContentNegotiation.repository;

import com.example.WorkingWithHeadersAndContentNegotiation.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
