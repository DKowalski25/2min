package com.github.DKowalski25._min.service.user;

import com.github.DKowalski25._min.controller.user.UserController;
import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.dto.user.UserUpdateDTO;
import com.github.DKowalski25._min.exceptions.BusinessValidationException;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.user.UserRepository;


import java.util.List;

/**
 * Service interface for managing {@link User} entities.
 *
 * <p>Provides business logic operations for user management including creation, retrieval,
 * update and deletion of users. All methods work with Data Transfer Objects (DTOs) to
 * decouple the service layer from persistence entities.</p>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Creating a user
 * UserRequestDTO newUser = new UserRequestDTO("username", "email@example.com", "password");
 * UserResponseDTO createdUser = userService.createUser(newUser);
 *
 * // Retrieving a user
 * UserResponseDTO user = userService.getUserById(1);
 * }</pre>
 * </p>
 *
 * @see UserController
 * @see UserRepository
 * @see UserRequestDTO
 * @see UserResponseDTO
 * @see UserUpdateDTO
 */
public interface UserService {

    /**
     * Creates a new user with the provided details.
     *
     * @param userDTO the user data to create (must not be {@code null})
     * @return the created user with generated identifier
     * @throws IllegalArgumentException if userDTO is {@code null} or contains invalid data
     * @throws BusinessValidationException if username or email already exists
     */
    UserResponseDTO createUser(UserRequestDTO userDTO);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the user identifier (must not be {@code null})
     * @return the found user
     * @throws IllegalArgumentException if id is {@code null}
     * @throws EntityNotFoundException if no user exists with the given id
     */
    UserResponseDTO getUserById(Integer id);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username to search for (must not be {@code null} or empty)
     * @return the found user
     * @throws IllegalArgumentException if username is {@code null} or empty
     * @throws EntityNotFoundException if no user exists with the given username
     */
    UserResponseDTO getUserByUsername(String username);

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address to search for (must not be {@code null} or empty)
     * @return the found user with basic information
     * @throws IllegalArgumentException if email is {@code null} or empty
     * @throws EntityNotFoundException if no user exists with the given email
     */
    UserResponseDTO getUserByEmail(String email);

    /**
     * Retrieves all users in the system.
     *
     * @return a list of all users with basic information (never {@code null}, may be empty)
     */
    List<UserResponseDTO> getAllUsers();

    /**
     * Updates an existing user's information.
     *
     * @param userDTO the user data to update (must not be {@code null})
     * @throws IllegalArgumentException if userDTO is {@code null} or contains invalid data
     * @throws EntityNotFoundException if no user exists to update
     * @throws BusinessValidationException if new username or email conflicts with existing users
     */
    void updateUser(UserUpdateDTO userDTO);

    /**
     * Deletes a user by their identifier.
     *
     * @param id the user identifier (must not be {@code null})
     * @throws IllegalArgumentException if id is {@code null}
     * @throws EntityNotFoundException if no user exists with the given id
     */
    void deleteUser(Integer id);
}