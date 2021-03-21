package com.momot.trakball.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class YourSquadsApi {
    @GetMapping("/your_squads")
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
