package com.events.photo.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.events.photo.models.Role;
import com.events.photo.models.entities.User;
import com.events.photo.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class DataInitialization {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeDefaultData() {
        if (userRepository.count() == 0
                && !userRepository.existsByUsername("admin@admin.com")) {
            User user = createUser();
            userRepository.save(user);
        }
    }

    private User createUser() {
        User employee = new User();
        employee.setUsername("admin@admin.com");
        employee.setPassword(passwordEncoder.encode("admin@admin.com"));
        employee.setRole(Role.ADMIN);
        return employee;
    }
}
