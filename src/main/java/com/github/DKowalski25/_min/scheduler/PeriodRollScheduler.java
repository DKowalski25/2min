package com.github.DKowalski25._min.scheduler;

import com.github.DKowalski25._min.service.timeblock.TimeBlockService;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PeriodRollScheduler {

    private final TimeBlockService timeBlockService;

    // Ежедневно в 00:01 - перенос периодов
    // Для тестирования
    // @Scheduled(cron = "0 */5 * * * *") // Каждые 5 минут
    @Scheduled(cron = "0 1 0 * * *")
    @Transactional
    public void rotateTimeBlocksDaily() {
        timeBlockService.rotateTimeBlocks();
    }
}
