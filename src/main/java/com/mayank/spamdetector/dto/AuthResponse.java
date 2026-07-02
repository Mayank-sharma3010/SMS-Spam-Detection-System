package com.mayank.spamdetector.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;

    private String message;

    private String role;

    private String email;

    private String fullName;

}