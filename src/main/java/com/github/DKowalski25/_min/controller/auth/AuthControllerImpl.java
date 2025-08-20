package com.github.DKowalski25._min.controller.auth;

import com.github.DKowalski25._min.config.security.jwt.JwtUtil;
import com.github.DKowalski25._min.dto.auth.LoginRequest;
import com.github.DKowalski25._min.dto.user.UserMapper;
import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.models.config.CustomUserDetails;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.service.user.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

            String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(token);
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body("Authentication failed");
        }
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(201).body(userService.createUser(dto));
    }
}
