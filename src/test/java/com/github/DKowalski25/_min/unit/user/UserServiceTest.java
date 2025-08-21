package com.github.DKowalski25._min.unit.user;

import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.dto.user.UserUpdateDTO;
import com.github.DKowalski25._min.exceptions.BusinessValidationException;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.models.UserRole;
import com.github.DKowalski25._min.repository.user.UserRepository;
import com.github.DKowalski25._min.service.timeblock.TimeBlockService;

import com.github.DKowalski25._min.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TimeBlockService timeBlockService;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserRequestDTO userRequestDTO;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@email.com")
                .password("encodedPassword")
                .role(UserRole.USER)
                .blocked(false)
                .build();

        userRequestDTO = new UserRequestDTO("testuser", "password", "test@email.com");
    }

    @Test
    void createUser_ShouldCreateUserSuccessfully() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(testUser);

        UserResponseDTO result = userService.createUser(userRequestDTO);

        assertNotNull(result);
        assertEquals("testuser", result.username());
        verify(timeBlockService).createDefaultTimeBlocks(any());
    }

    @Test
    void createUser_ShouldThrowExceptionWhenEmailExists() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(BusinessValidationException.class, () ->
                userService.createUser(userRequestDTO));
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));

        UserResponseDTO result = userService.getUserById(testUser.getId());

        assertNotNull(result);
        assertEquals(testUser.getId(), result.id());
    }

    @Test
    void getUserById_ShouldThrowExceptionWhenNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                userService.getUserById(UUID.randomUUID()));
    }

    @Test
    void updateUser_ShouldUpdateUserSuccessfully() {
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any())).thenReturn(testUser);

        UserUpdateDTO updateDTO = new UserUpdateDTO("newusername", null, "new@email.com", null);
        UserResponseDTO result = userService.updateUser(testUser.getId(), updateDTO);

        assertNotNull(result);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(any());

        assertDoesNotThrow(() -> userService.deleteUser(testUser.getId()));
        verify(userRepository).delete(testUser);
    }
}