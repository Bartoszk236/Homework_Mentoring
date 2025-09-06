package com.example.OwnDataSourceConfigurationAndConnectionPoolAndMonitoring.repository;

import com.example.OwnDataSourceConfigurationAndConnectionPoolAndMonitoring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT u.user_id, u.first_name, u.last_name " +
                    "FROM users u " +
                    "WHERE u.first_name = :username " +
                    "AND SLEEP(5) = 0",
            nativeQuery = true
    )
    User findByUsername(String username);
}
