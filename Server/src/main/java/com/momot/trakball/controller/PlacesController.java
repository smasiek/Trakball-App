package com.momot.trakball.controller;

import com.momot.trakball.dao.Place;
import com.momot.trakball.manager.PlaceManager;
import com.momot.trakball.security.jwt.AuthTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/places")
public class PlacesController {

    @Autowired
    private PlaceManager placeManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @GetMapping("/all")
    public Iterable<Place> getPlaces() {
        return placeManager.findAll();
    }

    @GetMapping
    public Optional<Place> getPlace(@RequestParam Long id) {
        return placeManager.findById(id);
    }

    @GetMapping("/cities")
    public Iterable<String> getCities(@RequestParam String city, @RequestParam String street,
                                      @RequestParam String place) {
        return placeManager.findCitiesByInput(city, street, place);
    }

    @GetMapping("/streets")
    public Iterable<String> getStreets(@RequestParam String city, @RequestParam String street,
                                       @RequestParam String place) {
        return placeManager.findStreetsByInput(city, street, place);
    }

    @GetMapping("/names")
    public Iterable<String> getPlaceNames(@RequestParam String city, @RequestParam String street,
                                          @RequestParam String place) {
        return placeManager.findNamesByInput(city, street, place);
    }

    @PostMapping
    public Place addPlace(@RequestBody Place place) {
        return placeManager.save(place);
    }

    @PutMapping
    public Place editPlace(@RequestBody Place place) {
        return placeManager.save(place);
    }

    @DeleteMapping
    public void deletePlace(@RequestParam Long id) {
        placeManager.deleteById(id);
    }
}