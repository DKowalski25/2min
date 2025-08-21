package com.github.DKowalski25._min.integration.user;

import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.exceptions.BusinessValidationException;
import com.github.DKowalski25._min.integration.AbstractIntegrationTest;
import com.github.DKowalski25._min.models.UserRole;
import com.github.DKowalski25._min.service.user.UserServiceImpl;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    @Transactional
    void createUser_ShouldPersistUserInDatabase() {
        UserRequestDTO request = new UserRequestDTO("integrationuser", "password123", "integration@test.com");

        UserResponseDTO result = userService.createUser(request);

        assertNotNull(result.id());
        assertEquals("integrationuser", result.username());
        assertEquals("integration@test.com", result.email());
        assertEquals(UserRole.USER, result.role());
        assertFalse(result.blocked());
    }

    @Test
    @Transactional
    void createUser_ShouldThrowExceptionForDuplicateEmail() {
        UserRequestDTO request1 = new UserRequestDTO("user1", "password123", "duplicate@test.com");
        UserRequestDTO request2 = new UserRequestDTO("user2", "password456", "duplicate@test.com");

        userService.createUser(request1);

        assertThrows(BusinessValidationException.class, () ->
                userService.createUser(request2));
    }

    @Test
    @Transactional
    void getUserByEmail_ShouldReturnCorrectUser() {
        UserRequestDTO request = new UserRequestDTO("emailuser", "password123", "email@test.com");
        UserResponseDTO created = userService.createUser(request);

        UserResponseDTO found = userService.getUserByEmail("email@test.com");

        assertEquals(created.id(), found.id());
        assertEquals(created.email(), found.email());
    }
}