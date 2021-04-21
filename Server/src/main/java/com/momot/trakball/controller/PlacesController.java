package com.momot.trakball.controller;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.manager.PlaceManager;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/places")
public class PlacesController {


    @Autowired
    private PlaceManager PlaceManager;

    @GetMapping("/all")
    public Iterable<Place> getPlaces(){
        return PlaceManager.findAll();
    }

    @GetMapping
    public Optional<Place> getPlace(@RequestParam Long id){
        return PlaceManager.findById(id);
    }

    @PostMapping
    public Place addPlace(@RequestBody Place place){
        return PlaceManager.save(place);
    }

    @PutMapping
    public Place editPlace(@RequestBody Place place){
        return PlaceManager.save(place);
    }

    @DeleteMapping
    public void deletePlace(@RequestParam Long id){
        PlaceManager.deleteById(id);
    }



}
