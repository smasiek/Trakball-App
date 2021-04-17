package com.momot.trakball.controller;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SquadsController {

    @Autowired
    private SquadRepository squadRepository;

   /* @GetMapping("/squads")
    public List<Squad> getSquads(){
        return squadRepository.getSquadList();
    }*/
   @GetMapping("/squads")
    public Iterable<Squad> getSquads(){
       return squadRepository.findAll();
   }
}
