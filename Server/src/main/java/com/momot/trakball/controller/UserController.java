package com.momot.trakball.controller;

import com.momot.trakball.dto.request.EditProfileRequest;
import com.momot.trakball.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @PutMapping
    public ResponseEntity<?> editProfile(@RequestHeader("Authorization") String header, @RequestBody EditProfileRequest editProfileRequest) {
        String jwt="";
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            jwt = header.substring(7, header.length());
        }
        return userManager.editProfile(editProfileRequest,jwt);

    }
}
