package com.events.photo.controllers;

import static com.events.photo.controllers.ControllerUtilService.buildResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.photo.exceptions.BadRequestException;
import com.events.photo.models.ApiResponse;
import com.events.photo.models.dtos.LoginRequestDto;
import com.events.photo.models.dtos.LoginResponseDto;
import com.events.photo.services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> authenticate(
            @Valid @RequestBody LoginRequestDto loginRequestDto) throws BadRequestException {

        LoginResponseDto loginResponseDto = authenticationService.authenticate(loginRequestDto);
        return buildResponse(HttpStatus.OK, loginResponseDto, "Login successful.");
    }
}
