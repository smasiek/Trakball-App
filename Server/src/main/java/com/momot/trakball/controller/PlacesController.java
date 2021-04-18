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
@RequestMapping("/places")
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

<<<<<<< Updated upstream:src/main/java/com/momot/trakball/controller/PlacesController.java
=======
    @DeleteMapping
    public void deletePlace(@RequestParam Long id){
        PlaceManager.deleteById(id);
    }

<<<<<<< Updated upstream:Server/src/main/java/com/momot/trakball/controller/PlacesController.java
    @GetMapping("/places")
    public Iterable<Place> getSquads(){
        return placeRepository.findAll();
    }

=======
>>>>>>> Stashed changes:Server/src/main/java/com/momot/trakball/controller/PlacesController.java
>>>>>>> Stashed changes:src/main/java/com/momot/trakball/controller/PlacesController.java
}
