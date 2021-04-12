package com.momot.trakball.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MapController {
    @GetMapping("/map")
    public String map(){
        return "map";
    }
}
