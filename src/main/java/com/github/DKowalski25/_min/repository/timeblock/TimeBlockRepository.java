package com.github.DKowalski25._min.repository.timeblock;

import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.TimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Integer> {
    Optional<TimeBlock> findByType(TimeType type);
    boolean existsByType(TimeType type);
}
