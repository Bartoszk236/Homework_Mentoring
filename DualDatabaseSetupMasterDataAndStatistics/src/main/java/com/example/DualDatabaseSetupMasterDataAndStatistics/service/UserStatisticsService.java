package com.example.DualDatabaseSetupMasterDataAndStatistics.service;

import com.example.DualDatabaseSetupMasterDataAndStatistics.analytics.entity.UserStatistics;
import com.example.DualDatabaseSetupMasterDataAndStatistics.analytics.repository.UserStatisticsRepository;
import com.example.DualDatabaseSetupMasterDataAndStatistics.primary.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserStatisticsService {
    private final UserStatisticsRepository userStatisticsRepository;

    @Async
    @Transactional("analyticsTransactionManager")
    public void saveUserStatistics(User user) {
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUserId(user.getId());
        userStatisticsRepository.save(userStatistics);
    }
}
