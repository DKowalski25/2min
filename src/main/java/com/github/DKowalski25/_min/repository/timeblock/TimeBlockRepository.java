package com.github.DKowalski25._min.repository.timeblock;

import com.github.DKowalski25._min.dto.taskblock.TimeBlockRequestDTO;
import com.github.DKowalski25._min.dto.taskblock.TimeBlockResponseDTO;
import com.github.DKowalski25._min.dto.taskblock.TimeBlockUpdateDTO;
import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.TimeType;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link TimeBlock} entities management.
 *
 * <p><strong>Note:</strong> This interface relies on Spring Data JPA capabilities.
 * All query methods return {@link Optional} or primitive results where appropriate.
 * Business exception handling should be performed at the service layer.</p>
 *
 * <p><strong>Usage example:</strong>
 * <pre>{@code
 * // In service layer:
 * TimeBlock timeBlockType = timeBlockRepository.findByType(timeType)
 *     .orElseThrow(() -> new EntityNotFoundException("TimeBlock not found"));
 * }</pre></p>
 *
 * @see TimeBlockResponseDTO
 * @see TimeBlockRequestDTO
 * @see TimeBlockUpdateDTO
 */
@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Integer> {

    /**
     * Finds a time block by its type.
     *
     * @param type the time block type to search for (must not be {@code null})
     * @return an {@link Optional} containing the found time block or empty if none exists
     */
    Optional<TimeBlock> findByType(TimeType type);

    /**
     * Checks whether a time block exists with the given type.
     *
     * @param type the time block type to check (must not be {@code null})
     * @return {@code true} if a time block exists, {@code false} otherwise
     */
    boolean existsByType(TimeType type);
}
