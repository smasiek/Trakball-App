package com.momot.trakball.api;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.manager.SquadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class SquadsApi {

    @Autowired
    private SquadManager squadManager;

    @GetMapping("/squads")
    public List<Squad> getSquads(){
        return squadManager.getSquadList();
    }
}
