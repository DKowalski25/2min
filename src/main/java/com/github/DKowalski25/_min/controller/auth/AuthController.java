package com.github.DKowalski25._min.controller.auth;

import com.github.DKowalski25._min.dto.auth.LoginRequest;
import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;

import org.springframework.http.ResponseEntity;

/**
 * Interface for the authentication and user registration controller.
 * Handles login and registration operations via the REST API.
 */
public interface AuthController {

    /**
     * User authentication by username and password.
     *
     * @param request object containing the username and password
     * @return JWT token in case of successful authentication
     */
    ResponseEntity<String> login(LoginRequest request);

    /**
     * Registration of a new user.
     *
     * @param dto object with user data (name, email, password, etc.)
     * @return created user in DTS format
     */
    ResponseEntity<UserResponseDTO> register(UserRequestDTO dto);
}
