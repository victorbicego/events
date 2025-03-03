package com.events.photo.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.events.photo.exceptions.BadRequestException;
import com.events.photo.models.Role;
import com.events.photo.models.dtos.LoginRequestDto;
import com.events.photo.models.dtos.LoginResponseDto;
import com.events.photo.services.AuthenticationService;
import com.events.photo.services.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl customUserDetailsService;

    @Override
    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) throws BadRequestException {
        authenticateCredentials(loginRequestDto);
        UserDetails foundUser =
                customUserDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        String jwtToken = jwtService.generateToken(foundUser);
        Role role = determineRole(foundUser);
        return new LoginResponseDto(jwtToken, foundUser.getUsername(), role);
    }

    private void authenticateCredentials(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(), loginRequestDto.getPassword()));
    }

    private Role determineRole(UserDetails userDetails) throws BadRequestException {
        String roleString =
                userDetails.getAuthorities().stream()
                        .findFirst()
                        .orElseThrow(() -> new BadRequestException("Invalid user role."))
                        .getAuthority();
        return Role.valueOf(roleString);
    }
}
