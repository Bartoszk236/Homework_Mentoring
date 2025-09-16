package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.MailLog;
import com.example.PracticalTasksAboutSpringDataJpa.repository.MailLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final MailLogRepository mailLogRepository;

    public void sentEmail(String to) {
        if (!to.contains("@"))
            throw new IllegalArgumentException("Invalid email address");

        MailLog mailLog = new MailLog();
        mailLog.setSentTo(to);
        mailLog.setDescription("SENT");
        mailLogRepository.save(mailLog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sentEmailSecondVersion(String to) {
        if (!to.contains("@"))
            throw new IllegalArgumentException("Invalid email address");

        MailLog mailLog = new MailLog();
        mailLog.setSentTo(to);
        mailLog.setDescription("SENT");
        mailLogRepository.save(mailLog);
    }
}
