package com.momot.trakball.controller;

import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.SquadDto;
import com.momot.trakball.dto.request.EditProfileRequest;
import com.momot.trakball.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PutMapping
    public ResponseEntity<?> editProfile(@RequestHeader("Authorization") String header, @RequestBody EditProfileRequest editProfileRequest) {
        String jwt = "";
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
        }
        return userManager.editProfile(editProfileRequest, jwt);

    }

    @GetMapping("/squads")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<SquadDto> getYourSquads() {
        return userManager.findYourSquads();
    }

    @GetMapping("/places")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<PlaceDto> getYourPlaces() {
        return userManager.findYourPlaces();
    }
}