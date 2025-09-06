package com.example.DualDatabaseSetupMasterDataAndStatistics.analytics.repository;

import com.example.DualDatabaseSetupMasterDataAndStatistics.analytics.entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
}
