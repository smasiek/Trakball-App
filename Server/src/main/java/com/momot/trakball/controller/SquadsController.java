package com.momot.trakball.controller;

import com.momot.trakball.dto.SquadDto;
import com.momot.trakball.dto.request.DeleteSquadRequest;
import com.momot.trakball.dto.request.GenerateSquadsRequest;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.momot.trakball.manager.SquadsUpdate.JOIN;
import static com.momot.trakball.manager.SquadsUpdate.LEAVE;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/squads")
public class SquadsController {

    private final SquadManager squadManager;

    final JwtUtils jwtUtils;

    @Autowired
    public SquadsController(SquadManager squadManager, JwtUtils jwtUtils) {
        this.squadManager = squadManager;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/place/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<SquadDto> getPlaceSquads(@RequestParam Long place_id) {
        return squadManager.findByPlace(place_id);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public SquadDto getSquad(@RequestParam Long squad_id) {
        return squadManager.findById(squad_id);
    }

    @GetMapping("/all")
    public Iterable<SquadDto> getSquads() {
        return squadManager.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addSquad(@RequestBody NewSquadRequest squadRequest) {
        return squadManager.addSquad(squadRequest);
    }

    @PostMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addSquads(@RequestBody GenerateSquadsRequest squadRequests) {
        return squadManager.addSquads(squadRequests.getSquadRequests());
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
    public ResponseEntity<?> deleteSquad(@RequestBody DeleteSquadRequest deleteSquadRequest) {
        return squadManager.deleteById(deleteSquadRequest);
    }
}