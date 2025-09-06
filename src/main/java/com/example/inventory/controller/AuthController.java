package com.example.inventory.controller;

import com.example.inventory.dto.request.LoginRequest;
import com.example.inventory.dto.request.SignUpRequest;
import com.example.inventory.dto.response.UserResponse;
import com.example.inventory.service.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid  @RequestBody SignUpRequest signUpRequest) {
        String token =  authService.registerUser(signUpRequest);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse userResponse = authService.authenticateUser(loginRequest);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                userResponse.getResponseCookie().toString()).body(userResponse);
    }

    @GetMapping("/username")
    public ResponseEntity<?> curretUserName(Authentication authentication){
        String response = authService.currentUserName(authentication);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(Authentication authentication){
        UserResponse userResponse = authService.getUserDetails(authentication);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser(){
        ResponseCookie cookie = authService.signOut();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                cookie.toString()).body("You've been signed out");
    }
}

