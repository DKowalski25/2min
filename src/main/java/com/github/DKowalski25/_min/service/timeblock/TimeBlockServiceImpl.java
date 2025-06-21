package com.github.DKowalski25._min.service.timeblock;

import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.TimeType;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.timeblock.TimeBlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeBlockServiceImpl implements TimeBlockService {
    private final TimeBlockRepository timeBlockRepository;
    @Override
    public void createDefaultTimeBlocks(User user) {
        List<TimeType> types = List.of(TimeType.values());
        for (TimeType type: types) {
            TimeBlock block = new TimeBlock();
            block.setType(type);
            block.setUser(user);
            timeBlockRepository.save(block);
        }
    }
}
