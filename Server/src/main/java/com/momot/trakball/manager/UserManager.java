package com.momot.trakball.manager;

import com.momot.trakball.dao.User;
import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.SquadDto;
import com.momot.trakball.dto.response.JwtResponse;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.UserRepository;
import com.momot.trakball.security.services.UserDetailsImpl;
import com.momot.trakball.utils.SquadValidator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserManager {

    private final UserRepository userRepository;

    final AuthenticationManager authenticationManager;
    private final CloudinaryManager cloudinaryManager;

    private final PasswordEncoder encoder;

    @Autowired
    public UserManager(UserRepository userRepository, AuthenticationManager authenticationManager, CloudinaryManager cloudinaryManager, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.cloudinaryManager = cloudinaryManager;
        this.encoder = encoder;
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

    public ResponseEntity<?> editProfile(Optional<String> email, Optional<String> password, Optional<String> name, Optional<String> surname, Optional<String> phone, Optional<String> oldEmail, Optional<String> oldPassword, Optional<MultipartFile> file, String jwt) {
        Optional<User> userFromContext = getUserFromContext();

        if (userFromContext.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Could not load user from context"));
        }

        User user = userFromContext.get();

        if (oldPassword.isEmpty() || !BCrypt.checkpw(oldPassword.get(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Request authentication failed!"));
        }

        if (email.isPresent()) {
            if (userRepository.existsByEmail(email.get())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }
            user.setEmail(email.get());
        }

        password.ifPresent(s -> user.setPassword(encoder.encode(s)));
        name.ifPresent(s -> user.getUserDetails().setName(s));
        surname.ifPresent(s -> user.getUserDetails().setSurname(s));
        phone.ifPresent(s -> user.getUserDetails().setPhone(s));
        if (file.isPresent()) {
            if (user.getPhoto() != null) {
                MultipartFile multipartFile = file.get();
                String[] photoUrlSplit = user.getPhoto().split("/");
                String publicIdWithExtension = photoUrlSplit[photoUrlSplit.length - 1];
                String publicId = publicIdWithExtension.substring(0, publicIdWithExtension.indexOf('.'));

                user.getUserDetails().setPhoto(cloudinaryManager.updateAvatarAndGetPublicId(multipartFile, publicId));
            } else {
                user.getUserDetails().setPhoto(cloudinaryManager.uploadAvatarAndGetId(file.get()));
            }
        }

        userRepository.save(user);

        Set<String> roles = user.getRoles().stream()
                .map(item -> item.getName().getText())
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getUserId(),
                user.getEmail(),
                user.getUserDetails().getName(),
                user.getUserDetails().getSurname(),
                user.getUserDetails().getPhone(),
                user.getPhoto(),
                roles));
    }

    public Optional<User> getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername());
    }

    public Iterable<SquadDto> findYourSquads() {
        return getUserFromContext().isPresent() ?
                new ArrayList<>(getUserFromContext().get().getSquads().stream().filter(SquadValidator::isSquadValid).map(squad ->
                        new SquadDto(squad.getSquad_id(),
                                squad.getSport(),
                                squad.getMembers().size(),
                                squad.getMaxMembers(),
                                squad.getFee(),
                                squad.getDate(),
                                squad.getCreator(),
                                squad.getPlace())
                ).collect(Collectors.toList())) : new ArrayList<>();
    }

    public Iterable<PlaceDto> findYourPlaces() {
        return getUserFromContext().isPresent() ?
                new ArrayList<>(getUserFromContext().get().getYourPlaces().stream().map(place ->
                        new PlaceDto(place.getPlace_id(),
                                place.getName(),
                                place.getCity(),
                                place.getPostal_code(),
                                place.getStreet(),
                                place.getLatitude(),
                                place.getLongitude(),
                                place.getPhoto()))
                        .collect(Collectors.toList())) : new ArrayList<>();
    }
}