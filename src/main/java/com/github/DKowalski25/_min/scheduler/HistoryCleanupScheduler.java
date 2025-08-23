package com.github.DKowalski25._min.scheduler;

import com.github.DKowalski25._min.models.HistoryRetention;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.user.UserRepository;
import com.github.DKowalski25._min.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoryCleanupScheduler {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void cleanupHistoryAutomatically() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            LocalDateTime cutoffDate = calculateCutoffDate(user.getHistoryRetention());
            if (cutoffDate != null) {
                taskService.cleanHistory(user.getId(), cutoffDate);
            }
        }
    }

    // Для тестирования
    // @Scheduled(cron = "0 */10 * * * *") // Каждые 10 минут
    // public void testCleanup() {
    //     cleanUpHistoryAutomatically();
    // }

    private LocalDateTime calculateCutoffDate(HistoryRetention retention) {
        return switch (retention) {
            case NEVER -> LocalDateTime.now();
            case WEEK -> LocalDateTime.now().minusWeeks(1);
            case MONTH -> LocalDateTime.now().minusMonths(1);
            case YEAR -> LocalDateTime.now().minusYears(1);
            case FOREVER -> null;
        };
    }
}
