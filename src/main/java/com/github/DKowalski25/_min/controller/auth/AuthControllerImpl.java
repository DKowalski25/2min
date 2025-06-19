package com.github.DKowalski25._min.controller.auth;

import com.github.DKowalski25._min.config.security.jwt.JwtUtil;
import com.github.DKowalski25._min.dto.auth.LoginRequest;
import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.service.user.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("Attempting login for: " + request.username());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            System.out.println("Authentication success for: " + request.username());
            String token = jwtUtil.generateToken(request.username());
            System.out.println("Generated token for: " + request.username());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            System.err.println("Login failed for: " + request.username());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Authentication failed");
        }
    }

    @Override
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(201).body(userService.createUser(dto));
    }
}
