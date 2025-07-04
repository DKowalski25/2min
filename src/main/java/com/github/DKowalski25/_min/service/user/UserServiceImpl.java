package com.github.DKowalski25._min.service.user;

import com.github.DKowalski25._min.dto.user.UserMapper;
import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.dto.user.UserUpdateDTO;
import com.github.DKowalski25._min.exceptions.BusinessValidationException;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.user.UserRepository;
import com.github.DKowalski25._min.service.timeblock.TimeBlockService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TimeBlockService timeBlockService;

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        validateUserDoesNotExist(userDTO.username(), userDTO.email());

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        User savedUser = userRepository.save(user);
        timeBlockService.createDefaultTimeBlocks(savedUser);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UserUpdateDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userMapper.updateUserFromDto(userDTO, existingUser);
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

    private void validateUserDoesNotExist(String username, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessValidationException("Email already in use");
        }

        if (userRepository.existsByUsername(username)) {
            throw new BusinessValidationException("User already exists");
        }
    }
}
