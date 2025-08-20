package com.github.DKowalski25._min.controller.user;

import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.dto.user.UserUpdateDTO;
import com.github.DKowalski25._min.models.config.CustomUserDetails;
import com.github.DKowalski25._min.repository.user.UserRepository;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * REST controller for managing user operations.
 *
 * <p>Provides endpoints for user CRUD operations and retrieval by various criteria.
 * All methods return {@link ResponseEntity} with appropriate HTTP status codes.</p>
 *
 * <p><strong>Usage example:</strong>
 * <pre>{@code
 * // Create user
 * UserRequestDTO newUser = new UserRequestDTO(...);
 * ResponseEntity<UserResponseDTO> response = userController.createUser(newUser);
 * }</pre></p>
 * @see UserController
 * @see UserRepository
 * @see UserRequestDTO
 * @see UserResponseDTO
 * @see UserUpdateDTO
 */
public interface UserController {

    /**
     * Creates a new user.
     *
     * @param userDto the user creation data (must not be {@code null})
     * @return {@link ResponseEntity} with created user data and HTTP status 201 (Created)
     *
     */
    ResponseEntity<UserResponseDTO> createUser(UserRequestDTO userDto);

//    /**
//     * Retrieves a user by ID.
//     *
//     * @param id the user identifier (must not be {@code null})
//     * @return {@link ResponseEntity} with user data and HTTP status 200 (OK),
//     *         or status 404 (Not Found) if user doesn't exist
//     */
//    ResponseEntity<UserResponseDTO> getUserById(UUID id);

    ResponseEntity<UserResponseDTO> getCurrentUser(CustomUserDetails userDetails);

    /**
     * Retrieves a user by username.
     *
     * @param username the username to search for (must not be {@code null} or empty)
     * @return {@link ResponseEntity} with user data and HTTP status 200 (OK),
     *         or status 404 (Not Found) if user doesn't exist
     */
    ResponseEntity<UserResponseDTO> getUserByUsername(String username);

    /**
     * Retrieves a user by email.
     *
     * @param email the email address to search for (must not be {@code null} or empty)
     * @return {@link ResponseEntity} with user data and HTTP status 200 (OK),
     *         or status 404 (Not Found) if user doesn't exist
     */
    ResponseEntity<UserResponseDTO> getUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return {@link ResponseEntity} with list of all users and HTTP status 200 (OK),
     *         or empty list if no users exist
     */
    ResponseEntity<List<UserResponseDTO>> getAll();

    /**
     * Updates an existing user.
     *
     * @param userDetails the user identifier to update (must not be {@code null})
     * @param userDto the user update data (must not be {@code null})
     * @return {@link ResponseEntity} with HTTP status 204 (No Content) if successful,
     *         or status 404 (Not Found) if user doesn't exist
     */
    ResponseEntity<Void> updateUser(CustomUserDetails userDetails, UserUpdateDTO userDto);

    /**
     * Deletes a user by ID.
     *
     * @param userDetails the user identifier to delete (must not be {@code null})
     * @return {@link ResponseEntity} with HTTP status 204 (No Content) if successful,
     *         or status 404 (Not Found) if user doesn't exist
     */
    ResponseEntity<Void> deleteUser(CustomUserDetails userDetails);
}
