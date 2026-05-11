package com.yugesh.safejourney.service;

import org.springframework.stereotype.Service;

import com.yugesh.safejourney.entities.User;
import com.yugesh.safejourney.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}