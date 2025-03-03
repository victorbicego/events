package com.events.photo.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.events.photo.models.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    @NotNull
    @Size(min = 1)
    private String jwtToken;

    @NotNull
    @Email
    private String username;

    @NotNull
    private Role role;
}
