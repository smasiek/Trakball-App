package com.momot.trakball.controller;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.dto.request.DeleteRequest;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.momot.trakball.manager.SquadsUpdate.JOIN;
import static com.momot.trakball.manager.SquadsUpdate.LEAVE;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/squads")
public class SquadsController {

    @Autowired
    private SquadManager squadManager;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/all")
    public Iterable<Squad> getSquads() {
        return squadManager.findAll();
    }

    @GetMapping("/place/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<Iterable<Squad>> getPlaceSquads(@RequestParam Long place_id) {
        return squadManager.findByPlace(place_id);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<Squad> getSquad(@RequestParam Long squad_id) {
        return squadManager.findById(squad_id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Squad addSquad(@RequestBody NewSquadRequest squadRequest) {
        return squadManager.addSquad(squadRequest);
    }

    @PostMapping("/join")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> joinSquad(@RequestParam Long squad_id) {
        return squadManager.updateSquad(squad_id, JOIN);
    }

    @PostMapping("/leave")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> leaveSquad(@RequestParam Long squad_id) {
        return squadManager.updateSquad(squad_id, LEAVE);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSquad(@RequestBody DeleteRequest deleteRequest) {
        return squadManager.deleteById(deleteRequest);
    }
}
