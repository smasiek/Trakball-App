package com.momot.trakball.controller;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.dto.request.EditProfileRequest;
import com.momot.trakball.dto.request.LoginRequest;
import com.momot.trakball.dto.response.JwtResponse;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.manager.UserManager;
import com.momot.trakball.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.header.Header;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserManager userManager;

/*
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<User> getSquad(@RequestParam Long id){
        return squadManager.findById(id);
    }
*/
    @PutMapping
    public ResponseEntity<?> editProfile(@RequestHeader("Authorization") String header, @RequestBody EditProfileRequest editProfileRequest) {
        String jwt="";
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            jwt = header.substring(7, header.length());
        }
        return userManager.editProfile(editProfileRequest,jwt);

    }
}
