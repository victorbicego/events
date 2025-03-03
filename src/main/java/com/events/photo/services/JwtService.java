package com.events.photo.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.events.photo.exceptions.InvalidJwtTokenException;

public interface JwtService {

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String getActiveUsername() throws InvalidJwtTokenException;
}
