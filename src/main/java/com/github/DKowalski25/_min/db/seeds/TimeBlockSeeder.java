package com.github.DKowalski25._min.db.seeds;

import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.TimeType;
import com.github.DKowalski25._min.repository.timeblock.TimeBlockRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeBlockSeeder {
    private final TimeBlockRepository timeBlockRepository;

    @PostConstruct
    public void seedTimeBlocks() {
        for (TimeType type: TimeType.values()) {
            boolean exists = timeBlockRepository.existsByType(type);
            if (!exists) {
                TimeBlock block = new TimeBlock();
                block.setType(type);
                timeBlockRepository.save(block);
            }
        }
    }


}
