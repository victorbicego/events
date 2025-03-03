package com.events.photo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.events.photo.models.ApiResponse;

public class ControllerUtilService {

    public static <T> ResponseEntity<ApiResponse<T>> buildResponse(
            HttpStatus status, T body, String message) {
        ApiResponse<T> response = new ApiResponse<>(status, body, message);
        return ResponseEntity.status(status).body(response);
    }
}
