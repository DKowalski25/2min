package com.github.DKowalski25._min.repository.user;

import com.github.DKowalski25._min.controller.user.UserController;
import com.github.DKowalski25._min.models.User;

import com.github.DKowalski25._min.service.user.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link User} entities management.
 *
 * <p><strong>Note:</strong> This interface relies on Spring Data JPA capabilities.
 * All query methods return {@link Optional} or primitive results where appropriate.
 * Business exception handling should be performed at the service layer.</p>
 *
 * <p><strong>Usage example:</strong>
 * <pre>{@code
 * // In service layer:
 * User user = userRepository.findByEmail(email)
 *     .orElseThrow(() -> new EntityNotFoundException("User not found"));
 * }</pre></p>
 *
 * @see UserService
 * @see UserController
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by email address.
     *
     * @param email the email address to search for (must not be {@code null})
     * @return an {@link Optional} containing the found user or empty if none exists
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by username.
     *
     * @param username the username to search for (must not be {@code null})
     * @return an {@link Optional} containing the found user or empty if none exists
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks whether a user exists with the given email address.
     *
     * @param email the email address to check (must not be {@code null})
     * @return {@code true} if a user exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks whether a user exists with the given username.
     *
     * @param username the username to check (must not be {@code null})
     * @return {@code true} if a user exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);
}
