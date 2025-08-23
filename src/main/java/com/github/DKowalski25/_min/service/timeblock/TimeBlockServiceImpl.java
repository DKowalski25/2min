package com.github.DKowalski25._min.service.timeblock;

import com.github.DKowalski25._min.models.Task;
import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.TimeType;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.task.TaskRepository;
import com.github.DKowalski25._min.repository.timeblock.TimeBlockRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeBlockServiceImpl implements TimeBlockService {

    private final TimeBlockRepository timeBlockRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public void createDefaultTimeBlocks(User user) {
        List<TimeType> types = List.of(TimeType.values());
        for (TimeType type: types) {
            TimeBlock block = new TimeBlock();
            block.setType(type);
            block.setUser(user);
            timeBlockRepository.save(block);
        }
    }

    @Override
    public void rotateTimeBlocks() {
        LocalDate today = LocalDate.now();
        List<TimeBlock> allBlocks = timeBlockRepository.findAll();

        for (TimeBlock block: allBlocks) {
            if (shouldRotateBlock(block.getType(), today)) {
                processBlockRotation(block);
            }
        }
    }

    private boolean shouldRotateBlock(TimeType type, LocalDate today) {
        return switch (type) {
            case DAY -> true;
            case WEEK -> today.getDayOfWeek() == DayOfWeek.MONDAY;
            case MONTH -> today.getDayOfMonth() == 1;
        };
    }

    private void processBlockRotation(TimeBlock block) {
        archiveAllCurrentTasks(block);
        activatePlannedTasks(block);
    }

    private void archiveAllCurrentTasks(TimeBlock block) {
        List<Task> currentTasks = taskRepository.findByTimeBlockAndPlannedForNext(block, false);

        for (Task task : currentTasks) {
            task.setArchivedAt(LocalDateTime.now());
            taskRepository.save(task);
        }
    }

    private void activatePlannedTasks(TimeBlock block) {
        List<Task> plannedTasks = taskRepository.findByTimeBlockAndPlannedForNext(block, true);

        for (Task task : plannedTasks) {
            task.setPlannedForNext(false); // меняем с true на false
            task.setPeriodMarker(generateCurrentPeriodMarker(block.getType()));
            taskRepository.save(task);
        }
    }

    private String generateCurrentPeriodMarker(TimeType type) {
        LocalDate today = LocalDate.now();

        switch (type) {
            case DAY:
                return today.format(DateTimeFormatter.ISO_LOCAL_DATE);
            case WEEK:
                int weekNumber = today.get(WeekFields.ISO.weekOfWeekBasedYear());
                return today.getYear() + "-W" + String.format("%02d", weekNumber);
            case MONTH:
                return today.getYear() + "-" + String.format("%02d", today.getMonthValue());
            default:
                throw new IllegalArgumentException("Unknown TimeType: " + type);
        }
    }
}
