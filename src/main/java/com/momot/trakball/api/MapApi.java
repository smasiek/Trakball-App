package com.momot.trakball.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MapApi {
    @GetMapping("/map")
    public String map(){
        return "map";
    }
}
