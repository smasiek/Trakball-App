package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.request.EditProfileRequest;
import com.momot.trakball.dto.response.JwtResponse;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.UserRepository;
import com.momot.trakball.security.services.UserDetailsImpl;
import com.momot.trakball.utils.SquadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserManager {

    private UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<?> editProfile(EditProfileRequest editProfileRequest, String jwt) {
        Optional<User> userFromContext = getUserFromContext();

        if (userFromContext.isPresent()) {

            User user = userFromContext.get();

            if (editProfileRequest.getEmail().isPresent()) {
                String email = editProfileRequest.getEmail().get();
                if (userRepository.existsByEmail(email)) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Email is already in use!"));
                }
                user.setEmail(email);
            }
            editProfileRequest.getPassword().ifPresent(user::setPassword);
            editProfileRequest.getName().ifPresent(user.getUserDetails()::setName);
            editProfileRequest.getSurname().ifPresent(user.getUserDetails()::setSurname);
            editProfileRequest.getPhone().ifPresent(user.getUserDetails()::setPhone);

            userRepository.save(user);

            Set<String> roles = user.getRoles().stream()
                    .map(item -> item.getName().getText())
                    .collect(Collectors.toSet());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    user.getUser_id(),
                    user.getEmail(),
                    user.getUserDetails().getName(),
                    user.getUserDetails().getSurname(),
                    user.getUserDetails().getPhone(),
                    roles));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Could not load user from context"));
    }

    public Optional<User> getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername());
    }

    public Iterable<Squad> findYourSquads() {
        return getUserFromContext().isPresent() ?
                getUserFromContext().get().getSquads().stream().filter(SquadValidator::isSquadValid).collect(Collectors.toList()) : new ArrayList<>();
    }
}