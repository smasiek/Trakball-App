package com.momot.trakball.controller;

import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.PlaceRequestDto;
import com.momot.trakball.dto.request.ApprovePlaceRequest;
import com.momot.trakball.dto.request.DeletePlaceRequest;
import com.momot.trakball.dto.request.DeletePlaceRequestRequest;
import com.momot.trakball.dto.request.NewPlaceRequest;
import com.momot.trakball.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.momot.trakball.service.PlaceFollow.FOLLOW;
import static com.momot.trakball.service.PlaceFollow.UNFOLLOW;

@RestController
@CrossOrigin
@RequestMapping("/api/places")
public class PlacesController {

    private final PlaceService placeService;

    @Autowired
    public PlacesController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/all")
    public Iterable<PlaceDto> getPlaces() {
        return placeService.findAll();
    }

    @GetMapping("/requests")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<PlaceRequestDto> getPlaceRequests() {
        return placeService.getPlaceRequests();
    }

    @PostMapping("/requests")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approvePlaceRequests(@RequestBody ApprovePlaceRequest approvedPlaceRequest) {
        return placeService.approvePlaceRequests(approvedPlaceRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePlace(@RequestBody DeletePlaceRequest deletePlaceRequest) {
        return placeService.deletePlace(deletePlaceRequest);
    }

    @DeleteMapping("/requests")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePlaceRequests(@RequestBody DeletePlaceRequestRequest deletePlaceRequest) {
        return placeService.deletePlaceRequests(deletePlaceRequest);
    }

    @GetMapping
    public PlaceDto getPlace(@RequestParam Long id) {
        return placeService.findById(id);
    }

    @GetMapping("/city")
    public Iterable<PlaceDto> getPlacesFromCity(@RequestParam String city) {
        return placeService.findByCity(city);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addPlace(@RequestBody NewPlaceRequest newPlaceRequest) {
        return placeService.addPlace(newPlaceRequest);
    }

    @PostMapping("/follow")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> followPlace(@RequestParam Long place_id) {
        return placeService.followPlace(place_id, FOLLOW);
    }

    @PostMapping("/unfollow")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> unfollowPlace(@RequestParam Long place_id) {
        return placeService.followPlace(place_id, UNFOLLOW);
    }

    @GetMapping("/cities")
    public Iterable<String> getCities(@RequestParam String city, @RequestParam String street,
                                      @RequestParam String place) {
        return placeService.findCitiesByInput(city, street, place);
    }

    @GetMapping("/streets")
    public Iterable<String> getStreets(@RequestParam String city, @RequestParam String street,
                                       @RequestParam String place) {
        return placeService.findStreetsByInput(city, street, place);
    }

    @GetMapping("/names")
    public Iterable<String> getPlaceNames(@RequestParam String city, @RequestParam String street,
                                          @RequestParam String place) {
        return placeService.findNamesByInput(city, street, place);
    }

    @PutMapping
    public ResponseEntity<?> updatePlacePhoto(@RequestParam Optional<Long> placeId, @RequestParam Optional<MultipartFile> file) {
        return placeService.updatePlacePhoto(placeId, file);
    }
}