package com.momot.trakball.controller;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/squads")
public class SquadsController {

    @Autowired
    private SquadManager squadManager;

   @GetMapping("/all")
   //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
   @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
   public Iterable<Squad> getSquads(){
       return squadManager.findAll();
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
