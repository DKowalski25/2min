package com.github.DKowalski25._min.controller;

import com.github.DKowalski25._min.config.security.jwt.JwtUtil;
import com.github.DKowalski25._min.controller.user.UserControllerImpl;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.models.UserRole;
import com.github.DKowalski25._min.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserControllerImpl.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser
    void getCurrentUser_ShouldReturnUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserResponseDTO userResponse = new UserResponseDTO(
                userId, "testuser", "test@email.com", UserRole.USER, false
        );

        when(userService.getUserById(userId)).thenReturn(userResponse);

        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void createUser_ShouldReturnCreated() throws Exception {
        UserResponseDTO userResponse = new UserResponseDTO(
                UUID.randomUUID(), "newuser", "new@email.com", UserRole.USER, false
        );

        when(userService.createUser(any())).thenReturn(userResponse);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "username": "newuser",
                                "password": "password123",
                                "email": "new@email.com"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    @WithMockUser
    void getUserByEmail_ShouldReturnUser() throws Exception {
        UserResponseDTO userResponse = new UserResponseDTO(
                UUID.randomUUID(), "testuser", "test@email.com", UserRole.USER, false
        );

        when(userService.getUserByEmail("test@email.com")).thenReturn(userResponse);

        mockMvc.perform(get("/api/v1/users/email/test@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }
}