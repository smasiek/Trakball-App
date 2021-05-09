package com.momot.trakball.controller;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.manager.UserManager;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.security.jwt.AuthTokenFilter;
import com.momot.trakball.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/squads")
public class SquadsController {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private SquadManager squadManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    JwtUtils jwtUtils;

   @GetMapping("/all")
   //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
   //@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
   public Iterable<Squad> getSquads(){
       return squadManager.findAll();
   }

    @GetMapping("/user/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    //@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<Squad> getSquads(@RequestParam String token){

       jwtUtils.validateJwtToken(token);

       logger.error("ID FROM TOKEN: {}", jwtUtils.getIdFromJwtToken(token));
       Long id = Long.parseLong(jwtUtils.getIdFromJwtToken(token));
        return userManager.findById(id).get().getSquads();
    }

   @GetMapping
   @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
   public Optional<Squad> getSquad(@RequestParam Long id){
       return squadManager.findById(id);
   }

   @PostMapping
   @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
   public Squad addSquad(@RequestBody Squad squad){
       return squadManager.save(squad);
   }

    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Squad editSquad(@RequestBody Squad squad){
        return squadManager.save(squad);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSquad(@RequestParam Long id){
       squadManager.deleteById(id);
    }

}
