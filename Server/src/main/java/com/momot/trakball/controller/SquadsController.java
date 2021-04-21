package com.momot.trakball.controller;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/squads")
public class SquadsController {

    @Autowired
    private SquadManager squadManager;


   @GetMapping("/all")
    public Iterable<Squad> getSquads(){
       return squadManager.findAll();
   }

   @GetMapping
    public Optional<Squad> getSquad(@RequestParam Long id){
       return squadManager.findById(id);
   }

   @PostMapping
    public Squad addSquad(@RequestBody Squad squad){
       return squadManager.save(squad);
   }

    @PutMapping
    public Squad editSquad(@RequestBody Squad squad){
        return squadManager.save(squad);
    }

    @DeleteMapping
    public void deleteSquad(@RequestParam Long id){
       squadManager.deleteById(id);
    }

}