package com.events.photo.services;

import com.events.photo.exceptions.BadRequestException;
import com.events.photo.models.dtos.LoginRequestDto;
import com.events.photo.models.dtos.LoginResponseDto;

public interface AuthenticationService {

    LoginResponseDto authenticate(LoginRequestDto loginRequestDto) throws BadRequestException;

}
