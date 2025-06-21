package com.github.DKowalski25._min.controller.user;

import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.dto.user.UserUpdateDTO;
import com.github.DKowalski25._min.repository.user.UserRepository;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userDto);

    /**
     * Retrieves a user by ID.
     *
     * @param id the user identifier (must not be {@code null})
     * @return {@link ResponseEntity} with user data and HTTP status 200 (OK),
     *         or status 404 (Not Found) if user doesn't exist
     */
    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id);

    /**
     * Retrieves a user by username.
     *
     * @param username the username to search for (must not be {@code null} or empty)
     * @return {@link ResponseEntity} with user data and HTTP status 200 (OK),
     *         or status 404 (Not Found) if user doesn't exist
     */
    @GetMapping("/username/{username}")
    ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username);

    /**
     * Retrieves a user by email.
     *
     * @param email the email address to search for (must not be {@code null} or empty)
     * @return {@link ResponseEntity} with user data and HTTP status 200 (OK),
     *         or status 404 (Not Found) if user doesn't exist
     */
    @GetMapping("/email/{email}")
    ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email);

    /**
     * Retrieves all users.
     *
     * @return {@link ResponseEntity} with list of all users and HTTP status 200 (OK),
     *         or empty list if no users exist
     */
    @GetMapping
    ResponseEntity<List<UserResponseDTO>> getAll();

    /**
     * Updates an existing user.
     *
     * @param id the user identifier to update (must not be {@code null})
     * @param userDto the user update data (must not be {@code null})
     * @return {@link ResponseEntity} with HTTP status 204 (No Content) if successful,
     *         or status 404 (Not Found) if user doesn't exist
     */
    @PatchMapping("/{id}")
    ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody @Valid UserUpdateDTO userDto);

    /**
     * Deletes a user by ID.
     *
     * @param id the user identifier to delete (must not be {@code null})
     * @return {@link ResponseEntity} with HTTP status 204 (No Content) if successful,
     *         or status 404 (Not Found) if user doesn't exist
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer id);
}
