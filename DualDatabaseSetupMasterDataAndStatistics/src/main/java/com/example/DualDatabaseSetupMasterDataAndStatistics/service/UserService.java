package com.example.DualDatabaseSetupMasterDataAndStatistics.service;

import com.example.DualDatabaseSetupMasterDataAndStatistics.primary.entity.User;
import com.example.DualDatabaseSetupMasterDataAndStatistics.primary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserStatisticsService userStatisticsService;

    @Transactional("primaryTransactionManager")
    public User createUser(User user) {
        User result = userRepository.save(user);
        userStatisticsService.saveUserStatistics(result);
        return result;
    }
}
