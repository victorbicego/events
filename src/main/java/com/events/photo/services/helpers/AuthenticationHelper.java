package com.events.photo.services.helpers;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.events.photo.exceptions.NotFoundException;
import com.events.photo.models.entities.User;
import com.events.photo.repositories.UserRepository;
import com.events.photo.services.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationHelper {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public User getAuthenticatedUser() throws NotFoundException {
        String username = jwtService.getActiveUsername();
        return userRepository.findByUsername(username).orElseThrow(
                () ->
                        new NotFoundException(
                                String.format("No user found with username: '%s'.", username)));
    }
}
