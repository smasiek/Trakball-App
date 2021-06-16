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
   public Iterable<Squad> getSquads(){
           return squadManager.findAll();
   }

    @GetMapping("/user/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<Squad> getYourSquads(){
        return squadManager.findByMember();
    }

    @GetMapping("/place/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<Iterable<Squad>> getPlaceSquads(@RequestParam Long id){
        return squadManager.findByPlace(id);
    }

   @GetMapping
   @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
   public Optional<Squad> getSquad(@RequestParam Long id){
       return squadManager.findById(id);
   }

   @PostMapping
   @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
   public Squad addSquad(@RequestBody NewSquadRequest squadRequest){
       return squadManager.addSquad(squadRequest);
   }

    @PostMapping("/join")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> joinSquad(@RequestParam Long id){
        return squadManager.updateSquad(id,JOIN);
    }

    @PostMapping("/leave")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> leaveSquad(@RequestParam Long id){
        return squadManager.updateSquad(id,LEAVE);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSquad(@RequestBody DeleteRequest deleteRequest){
       return squadManager.deleteById(deleteRequest);
    }


}
