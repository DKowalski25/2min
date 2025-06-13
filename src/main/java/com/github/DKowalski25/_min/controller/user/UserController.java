package com.github.DKowalski25._min.controller.user;

import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.dto.user.UserUpdateDTO;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface UserController {
    @PostMapping
    ResponseEntity<UserResponseDTO> createUser(UserRequestDTO userDto);

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> getUserById(Integer id);

    @GetMapping("/username/{username}")
    ResponseEntity<UserResponseDTO> getUserByUsername(String username);

    @GetMapping("/email/{email}")
    ResponseEntity<UserResponseDTO> getUserByEmail(String email);

    @GetMapping
    ResponseEntity<List<UserResponseDTO>> getAll();

    @PutMapping
    ResponseEntity<Void> updateUser(Integer id, UserUpdateDTO userDto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(Integer id);

}
