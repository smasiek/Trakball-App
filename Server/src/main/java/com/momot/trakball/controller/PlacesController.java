package com.momot.trakball.controller;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.request.NewPlaceRequest;
import com.momot.trakball.manager.PlaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.momot.trakball.manager.PlaceFollow.FOLLOW;
import static com.momot.trakball.manager.PlaceFollow.UNFOLLOW;

@RestController
@CrossOrigin
@RequestMapping("/api/places")
public class PlacesController {

    private final PlaceManager placeManager;

    @Autowired
    public PlacesController(PlaceManager placeManager) {
        this.placeManager = placeManager;
    }

    @GetMapping("/all")
    public Iterable<PlaceDto> getPlaces() {
        return placeManager.findAll();
    }

    @GetMapping
    public PlaceDto getPlace(@RequestParam Long id) {
        return placeManager.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addPlace(@RequestBody NewPlaceRequest newPlaceRequest) {
        return placeManager.addPlace(newPlaceRequest);
    }

    @PostMapping("/follow")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> followPlace(@RequestParam Long place_id) {
        return placeManager.followPlace(place_id, FOLLOW);
    }

    @PostMapping("/unfollow")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> unfollowPlace(@RequestParam Long place_id) {
        return placeManager.followPlace(place_id, UNFOLLOW);
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

    @PutMapping
    public Place editPlace(@RequestBody Place place) {
        return placeManager.save(place);
    }

    @DeleteMapping
    public void deletePlace(@RequestParam Long id) {
        placeManager.deleteById(id);
    }
}