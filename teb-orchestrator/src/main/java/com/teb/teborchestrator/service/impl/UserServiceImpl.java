package com.teb.teborchestrator.service.impl;

import com.teb.teborchestrator.model.entity.User;
import com.teb.teborchestrator.repository.UserRepository;
import com.teb.teborchestrator.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User with such username is already exist");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with such email is already exist");
        }

        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
