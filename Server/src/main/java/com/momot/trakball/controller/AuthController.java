package com.momot.trakball.controller;

import com.momot.trakball.dto.request.LoginRequest;
import com.momot.trakball.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam Optional<String> email, @RequestParam Optional<String> password, @RequestParam Optional<String> name, @RequestParam Optional<String> surname, @RequestParam Optional<String> phone, @RequestParam Optional<MultipartFile> file) {
        return authService.registerUser(email, password, name, surname, phone, file);
    }
}