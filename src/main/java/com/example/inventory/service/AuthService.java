package com.example.inventory.service;

import com.example.inventory.dto.request.SignUpRequest;
import jakarta.validation.Valid;

public interface AuthService {
    String registerUser(@Valid SignUpRequest signUpRequest);
}
