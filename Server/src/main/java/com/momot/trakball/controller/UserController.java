package com.momot.trakball.controller;

import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.SquadDto;
import com.momot.trakball.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> editProfile(@RequestHeader("Authorization") String header, @RequestParam Optional<String> email, @RequestParam Optional<String> password, @RequestParam Optional<String> name, @RequestParam Optional<String> surname, @RequestParam Optional<String> phone, @RequestParam Optional<String> oldEmail, @RequestParam Optional<String> oldPassword, @RequestParam("file") Optional<MultipartFile> file) {
        String jwt = "";
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
        }
        return userService.editProfile(email, password, name, surname, phone, oldEmail, oldPassword, file, jwt);
    }

    @GetMapping("/squads")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<SquadDto> getYourSquads() {
        return userService.findYourSquads();
    }

    @GetMapping("/places")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<PlaceDto> getYourPlaces() {
        return userService.findYourPlaces();
    }
}