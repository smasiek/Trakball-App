package com.momot.trakball.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/your_squads")
public class YourSquadsController {

/*    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Squad addSquad(@RequestBody NewSquadRequest squadRequest, @RequestParam String token){
        return squadManager.addSquad(squadRequest,token);
    }*/

    @GetMapping("/api/your_squads")
    public String[] your_squads(){
        String[] squads={"{" +
                "id: 1" +
                "creator: \"Janek Snow\"" +
                "place: \"Hala Krakowska\"" +
                "termin: \"17:00 20.03.2021\"" +
                "koszt: \"10zł\"" +
                "}",
                "{" +
                        "id: 2" +
                        "creator: \"Janek Snow\"" +
                        "place: \"Hala\"" +
                        "termin: \"18:00 20.03.2021\"" +
                        "koszt: \"15zł\"" +
                        "}"
        };

        return squads;
    }
}
