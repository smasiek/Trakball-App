package com.momot.trakball.controller;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.security.jwt.AuthTokenFilter;
import com.momot.trakball.security.jwt.JwtUtils;
import com.momot.trakball.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import static com.momot.trakball.manager.SquadsUpdate.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/squads")
public class SquadsController {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

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
    //@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<Squad> getYourSquads(){
        return squadManager.findByMember();
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
   /*
    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Squad editSquad(@RequestBody NewSquadRequest squadRequest){
        return squadManager.save(squadRequest);
    }
*/
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSquad(@RequestParam Long id){
       squadManager.deleteById(id);
    }


}
