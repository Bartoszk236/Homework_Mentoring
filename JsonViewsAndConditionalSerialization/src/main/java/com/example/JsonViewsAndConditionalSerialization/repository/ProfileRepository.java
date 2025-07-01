package com.example.JsonViewsAndConditionalSerialization.repository;

import com.example.JsonViewsAndConditionalSerialization.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
}
