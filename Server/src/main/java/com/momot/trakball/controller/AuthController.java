package com.momot.trakball.controller;

import com.momot.trakball.dao.ERole;
import com.momot.trakball.dao.Role;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.request.LoginRequest;
import com.momot.trakball.dto.response.JwtResponse;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.manager.CloudinaryManager;
import com.momot.trakball.repository.RoleRepository;
import com.momot.trakball.repository.UserDetailsRepository;
import com.momot.trakball.repository.UserRepository;
import com.momot.trakball.security.jwt.JwtUtils;
import com.momot.trakball.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final CloudinaryManager cloudinaryManager;

    final UserRepository userRepository;
    final UserDetailsRepository userDetailsRepository;
    final RoleRepository roleRepository;

    final PasswordEncoder encoder;
    final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, CloudinaryManager cloudinaryManager, UserRepository userRepository, UserDetailsRepository userDetailsRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.cloudinaryManager = cloudinaryManager;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

        if (user.isPresent()) {
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    user.get().getUserDetails().getName(),
                    user.get().getUserDetails().getSurname(),
                    user.get().getUserDetails().getPhone(),
                    user.get().getPhoto(),
                    roles));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User doesn't exist!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam Optional<String> email, @RequestParam Optional<String> password, @RequestParam Optional<String> name, @RequestParam Optional<String> surname, @RequestParam Optional<String> phone, @RequestParam Optional<MultipartFile> file) {

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || phone.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Necessary parameters missing"));
        }

        if (userRepository.existsByEmail(email.get())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(
                email.get(),
                encoder.encode(password.get()),
                name.get(),
                surname.get(),
                phone.get(),
                (file.isEmpty()) ? "https://ui-avatars.com/api/name=" + name.get() +
                        "%20" + surname.get() + "&background=random" : cloudinaryManager.uploadAvatarAndGetId(file.get()));

        Set<Role> roles = Collections.singleton(roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));

       /* Set<String> strRoles =new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }*/
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}