package com.github.DKowalski25._min.controler.auth;

import com.github.DKowalski25._min.dto.auth.LoginRequest;
import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interface for the authentication and user registration controller.
 * Handles login and registration operations via the REST API.
 */
@RequestMapping("/api/auth")
public interface AuthController {

    /**
     * User authentication by username and password.
     *
     * @param request object containing the username and password
     * @return JWT token in case of successful authentication
     */
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginRequest request);

    /**
     * Registration of a new user.
     *
     * @param dto object with user data (name, email, password, etc.)
     * @return created user in DTS format
     */
    @PostMapping("/register")
    ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO dto);
}
