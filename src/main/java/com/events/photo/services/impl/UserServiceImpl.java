package com.events.photo.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.events.photo.repositories.UserRepository;
import com.events.photo.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
}
