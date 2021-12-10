package com.momot.trakball.manager;

import com.momot.trakball.dao.*;
import com.momot.trakball.dto.CommentDto;
import com.momot.trakball.dto.SquadDto;
import com.momot.trakball.dto.UserDto;
import com.momot.trakball.dto.request.DeleteSquadRequest;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.CommentRepository;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.security.jwt.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static com.momot.trakball.dao.ERole.ROLE_ADMIN;

@Service
public class SquadManager {

    private final SquadRepository squadRepository;
    private final CommentRepository commentRepository;

    private final UserManager userManager;
    private final PlaceManager placeManager;

    final JwtUtils jwtUtils;

    @Autowired
    public SquadManager(SquadRepository squadRepository, CommentRepository commentRepository, UserManager userManager, PlaceManager placeManager, JwtUtils jwtUtils) {
        this.squadRepository = squadRepository;
        this.commentRepository = commentRepository;
        this.userManager = userManager;
        this.placeManager = placeManager;
        this.jwtUtils = jwtUtils;
    }

    public SquadDto findById(Long id) {
        SquadDto squadDto = new SquadDto();
        squadRepository.findById(id).ifPresent(squad -> {
            squadDto.setSquad_id(squad.getSquad_id());
            squadDto.setSport(squad.getSport());
            squadDto.setMaxMembers(squad.getMaxMembers());
            squadDto.setFee(squad.getFee());
            squadDto.setDate(squad.getDate());
            squadDto.setCreator(squad.getCreator());
            squadDto.setPlace(squad.getPlace());
        });
        return squadDto;
    }

    public List<UserDto> findMembers(Long id) {
        Optional<Squad> squad = squadRepository.findById(id);
        List<UserDto> resultSquads = new ArrayList<>();
        if (squad.isPresent()) {
            resultSquads = squad.get().getMembers().stream().map(
                    c -> new UserDto(c.getUserId(), c.getName(), c.getSurname(), c.getEmail(), c.getPhone(), c.getPhoto())
            ).collect(Collectors.toList());
        }
        return resultSquads;
    }

    public List<SquadDto> findAll() {
        Optional<List<Squad>> squads = squadRepository.findWithDateAfterToday();
        List<SquadDto> resultSquads = new ArrayList<>();
        if (squads.isPresent()) {
            resultSquads = squads.get().stream().filter(s -> s.getMembers().size() < s.getMaxMembers()).map(
                    s -> new SquadDto(s.getSquad_id(),
                            s.getSport(),
                            s.getMembers().size(),
                            s.getMaxMembers(),
                            s.getFee(), s.getDate(),
                            s.getCreator(),
                            s.getPlace())
            ).collect(Collectors.toList());
        }
        return resultSquads;
    }

    public Iterable<SquadDto> findByPlace(Long id) {
        Place place = new Place(placeManager.findById(id));
        Optional<Iterable<Squad>> foundSquads = squadRepository.findByPlace(place);
        List<Squad> result = new ArrayList<>();
        foundSquads.ifPresent(squads -> squads.forEach(result::add));

        return result.stream().map(
                s -> new SquadDto(s.getSquad_id(),
                        s.getSport(),
                        s.getMembers().size(),
                        s.getMaxMembers(),
                        s.getFee(), s.getDate(),
                        s.getCreator(),
                        s.getPlace())
        ).collect(Collectors.toList());
    }

    public ResponseEntity<?> addSquad(NewSquadRequest newSquadRequestSquad) {
        Optional<User> creator = userManager.getUserFromContext();

        Optional<Place> place = placeManager.findByNameAndStreetAndCity(newSquadRequestSquad.getPlace(),
                newSquadRequestSquad.getStreet(), newSquadRequestSquad.getCity());
        if (place.isPresent() && creator.isPresent()) {
            Squad squad = new Squad(null, newSquadRequestSquad.getSport(), newSquadRequestSquad.getMaxMembers(),
                    newSquadRequestSquad.getFee(), newSquadRequestSquad.getDate(), newSquadRequestSquad.isSecured(),
                    (newSquadRequestSquad.isSecured()) ? BCrypt.hashpw(newSquadRequestSquad.getPassword(),
                            BCrypt.gensalt()) : "", creator.get(), place.get());
            save(squad);
            return ResponseEntity.ok(new SquadDto(squad.getSquad_id(),
                    squad.getSport(),
                    squad.getMembers().size(),
                    squad.getMaxMembers(),
                    squad.getFee(), squad.getDate(),
                    squad.getCreator(),
                    squad.getPlace()));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Creating squad failed!"));
        }
    }

    public ResponseEntity<?> addSquads(List<NewSquadRequest> squadRequests) {
        Optional<User> creator = userManager.getUserFromContext();
        if (creator.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("User session error!"));
        }

        int failCounter = 0;
        int successCounter = 0;
        StringBuilder failMessages = new StringBuilder();
        for (NewSquadRequest squadRequest : squadRequests) {
            Optional<Place> place = placeManager.findByNameAndStreetAndCity(squadRequest.getPlace(),
                    squadRequest.getStreet(), squadRequest.getCity());
            try {
                if (place.isPresent()) {
                    Squad squad = new Squad(null, squadRequest.getSport(), squadRequest.getMaxMembers(),
                            squadRequest.getFee(), squadRequest.getDate(), false, "", creator.get(), place.get());
                    save(squad);
                    successCounter++;
                }
            } catch (ConstraintViolationException e) {
                Set<ConstraintViolation<?>> exceptions = e.getConstraintViolations();
                for (ConstraintViolation<?> exception : exceptions) {
                    failMessages.append(" ").append(exception.getMessageTemplate()).append(";");
                }
                failCounter++;
            }
        }

        if (successCounter > 0) {
            String successMessage = "Created " + successCounter + " squads";
            if (failCounter > 0) {
                successMessage += " with " + failCounter + " errors. Error messages: \"" + failMessages;
            }
            return ResponseEntity.ok(new MessageResponse(successMessage));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Creation of squads failed!" + failMessages));
        }
    }

    public Squad save(Squad squad) throws ConstraintViolationException {
        return squadRepository.save(squad);
    }

    public ResponseEntity<?> deleteById(DeleteSquadRequest deleteSquadRequest) {
        Optional<User> user = userManager.getUserFromContext();
        Optional<Squad> squad = squadRepository.findById(deleteSquadRequest.getSquad_id());

        if (squad.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Squad not found 🤐"));
        }

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("User session problem"));
        }

        if (!user.get().getRoles().contains(new Role(ROLE_ADMIN)) && !user.get().getUserId().equals(squad.get().getCreator().getUserId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("You have to be Admin or creator of a team"));
        }

        squadRepository.deleteById(deleteSquadRequest.getSquad_id());
        return ResponseEntity.ok("Squad deleted");
    }

    public ResponseEntity<?> updateSquad(Long squadId, SquadsUpdate updateType) {
        Optional<User> user = userManager.getUserFromContext();
        Optional<Squad> squad = squadRepository.findById(squadId);

        if (user.isEmpty() || squad.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("No user in context or squad doesn't exist!"));
        }

        Set<Squad> newSquads = user.get().getSquads();

        switch (updateType) {
            case LEAVE: {
                if (newSquads.contains(squad.get())) {
                    squad.ifPresent(newSquads::remove);
                    updateUserSquads(user, newSquads);
                    return ResponseEntity.ok(new MessageResponse("You've left squad."));
                }
                return ResponseEntity.badRequest().body(new MessageResponse("You can't leave this squad! You are not a member"));
            }
            case JOIN: {
                if (newSquads.contains(squad.get())) {
                    return ResponseEntity.badRequest().body(new MessageResponse("You are in this squad already!"));
                } else {
                    if (squad.get().getMembers().size() >= squad.get().getMaxMembers())
                        return ResponseEntity.badRequest().body(new MessageResponse("Squad is full"));

                    squad.ifPresent(newSquads::add);
                    updateUserSquads(user, newSquads);
                    return ResponseEntity.ok(new MessageResponse("You've joined squad successfully! 🙂"));
                }
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Bad update type"));
    }

    private void updateUserSquads(Optional<User> user, Set<Squad> newSquads) {
        user.ifPresent(u -> u.setSquads(newSquads));
        user.ifPresent(userManager::save);
    }

    public List<CommentDto> findCommentsById(Long squad_id) {
        Optional<Squad> squad = squadRepository.findById(squad_id);
        List<CommentDto> result = new ArrayList<>();
        squad.ifPresent(value -> result.addAll(value.getComments().stream().map(
                CommentDto::new
        ).sorted().collect(Collectors.toList())));
        Collections.reverse(result);
        return result;
    }

    public ResponseEntity<?> addComment(Long squad_id, CommentDto commentDto) {
        Optional<Squad> squad = squadRepository.findById(squad_id);
        Optional<User> user = userManager.getUserFromContext();

        if (squad.isPresent() && user.isPresent()) {
            commentRepository.save(new Comment(commentDto.getText(), commentDto.getDate(), user.get(), squad.get()));
            return ResponseEntity.ok(new MessageResponse("You've posted a comment! 🙂"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Comment wasn't posted. Something went wrong."));
    }

    public ResponseEntity<?> getSecuredInfo(Long squad_id) {
        Optional<Squad> optionalSquad = squadRepository.findById(squad_id);
        return optionalSquad.map(squad -> ResponseEntity.ok(new MessageResponse(String.valueOf(squad.isSecured())))).orElseGet(() -> ResponseEntity.badRequest().body(new MessageResponse("Couldn't get info about squad")));
    }

    public ResponseEntity<?> verifyPassword(Long squad_id, String password) {
        Optional<Squad> optionalSquad = squadRepository.findById(squad_id);
        if (optionalSquad.isPresent()) {
            if (BCrypt.checkpw(password, optionalSquad.get().getPassword())) {
                return ResponseEntity.ok(new MessageResponse(String.valueOf(true)));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Wrong password!"));
    }
}