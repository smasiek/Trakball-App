package com.momot.trakball.controller;

import com.momot.trakball.dto.SquadDto;
import com.momot.trakball.dto.UserDto;
import com.momot.trakball.dto.request.DeleteSquadRequest;
import com.momot.trakball.dto.request.GenerateSquadsRequest;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.service.SquadService;
import com.momot.trakball.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.momot.trakball.service.SquadsUpdate.JOIN;
import static com.momot.trakball.service.SquadsUpdate.LEAVE;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/squads")
public class SquadsController {

    private final SquadService squadService;

    final JwtUtils jwtUtils;

    @Autowired
    public SquadsController(SquadService squadService, JwtUtils jwtUtils) {
        this.squadService = squadService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/place/all")
    public Iterable<SquadDto> getPlaceSquads(@RequestParam Long place_id) {
        return squadService.findByPlace(place_id);
    }

    @GetMapping
    public SquadDto getSquad(@RequestParam Long squad_id) {
        return squadService.findById(squad_id);
    }

    @GetMapping("/secured")
    public ResponseEntity<?> getSecured(@RequestParam Long squad_id) {
        return squadService.getSecuredInfo(squad_id);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyPassword(@RequestParam Long squad_id, @RequestParam String password) {
        return squadService.verifyPassword(squad_id, password);
    }

    @GetMapping("/all")
    public Iterable<SquadDto> getSquads() {
        return squadService.findAll();
    }

    @GetMapping("/members")
    public List<UserDto> getSquadMembers(@RequestParam Long squad_id) {
        return squadService.findMembers(squad_id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addSquad(@RequestBody NewSquadRequest squadRequest) {
        return squadService.addSquad(squadRequest);
    }

    @PostMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addSquads(@RequestBody GenerateSquadsRequest squadRequests) {
        return squadService.addSquads(squadRequests.getSquadRequests());
    }

    @PostMapping("/join")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> joinSquad(@RequestParam Long squad_id) {
        return squadService.updateSquad(squad_id, JOIN);
    }

    @PostMapping("/leave")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> leaveSquad(@RequestParam Long squad_id) {
        return squadService.updateSquad(squad_id, LEAVE);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSquad(@RequestBody DeleteSquadRequest deleteSquadRequest) {
        return squadService.deleteById(deleteSquadRequest);
    }
}