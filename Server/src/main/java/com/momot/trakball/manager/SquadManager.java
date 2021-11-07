package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.SquadDto;
import com.momot.trakball.dto.request.DeleteSquadRequest;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SquadManager {

    private final SquadRepository squadRepository;

    private final UserManager userManager;

    private final PlaceManager placeManager;

    final JwtUtils jwtUtils;

    @Autowired
    public SquadManager(SquadRepository squadRepository, UserManager userManager, PlaceManager placeManager, JwtUtils jwtUtils) {
        this.squadRepository = squadRepository;
        this.userManager = userManager;
        this.placeManager = placeManager;
        this.jwtUtils = jwtUtils;
    }

    public SquadDto findById(Long id) {
        SquadDto squadDto = new SquadDto();
        squadRepository.findById(id).ifPresent(squad -> {
            // squadDto = new SquadDto(squad.getSquad_id(),squad.getSport(),squad.getMaxMembers(),squad.getFee(),squad.getDate(),squad.getCreator(),squad.getPlace());
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

    public List<SquadDto> findAll() {
        Optional<List<Squad>> squads = squadRepository.findWithDateAfterToday();
        List<SquadDto> resultSquads = new ArrayList<>();
        if (squads.isPresent()) {
            resultSquads = squads.get().stream().filter(s -> s.getMembers().size() < s.getMaxMembers()).map(
                    s -> new SquadDto(s.getSquad_id(),
                            s.getSport(),
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
                    newSquadRequestSquad.getFee(), newSquadRequestSquad.getDate(), creator.get(), place.get());
            save(squad);
            return ResponseEntity.ok(new SquadDto(squad.getSquad_id(),
                    squad.getSport(),
                    squad.getMaxMembers(),
                    squad.getFee(), squad.getDate(),
                    squad.getCreator(),
                    squad.getPlace()));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Creating squad failed!"));
        }
    }

    public Squad save(Squad squad) {
        return squadRepository.save(squad);
    }

    public ResponseEntity<?> deleteById(DeleteSquadRequest deleteSquadRequest) {
        if (squadRepository.existsById(deleteSquadRequest.getId())) {
            squadRepository.deleteById(deleteSquadRequest.getId());
            return ResponseEntity.ok("Squad deleted");
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Squad not found 🤐"));
    }

    public ResponseEntity<?> updateSquad(Long squadId, SquadsUpdate updateType) {
        Optional<User> user = userManager.getUserFromContext();
        Optional<Squad> squad = squadRepository.findById(squadId);

        if (checkUserAndSquadEmpty(user, squad)) {
            return ResponseEntity.badRequest().body(new MessageResponse("No user in context or squad doesn't exist!"));
        }

        Set<Squad> newSquads = user.get().getSquads();

        switch (updateType) {
            case LEAVE: {
                if (newSquads.contains(squad.get())) {
                    squad.ifPresent(newSquads::remove);
                    updateUserSquads(user, newSquads);
                    return ResponseEntity.ok(new MessageResponse("You've left squad!"));
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
                    return ResponseEntity.ok(new MessageResponse("You've joined squad!"));
                }
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Bad update type"));
    }

    private boolean checkUserAndSquadEmpty(Optional<User> user, Optional<Squad> squad) {
        return user.isEmpty() || squad.isEmpty();
    }

    private void updateUserSquads(Optional<User> user, Set<Squad> newSquads) {
        user.ifPresent(u -> u.setSquads(newSquads));
        user.ifPresent(userManager::save);
    }
}