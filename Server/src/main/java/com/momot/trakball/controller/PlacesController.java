package com.momot.trakball.controller;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dto.request.NewSquadSuggestionsRequest;
import com.momot.trakball.manager.PlaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/places")
public class PlacesController {


    @Autowired
    private PlaceManager placeManager;

    @GetMapping("/all")
    public Iterable<Place> getPlaces(){
        return placeManager.findAll();
    }

    @GetMapping
    public Optional<Place> getPlace(@RequestParam Long id){
        return placeManager.findById(id);
    }

    @GetMapping("/cities")
    public Iterable<String> getCities(@RequestBody NewSquadSuggestionsRequest newSquadSuggestionsRequest){
        return placeManager.findCitiesByInput(newSquadSuggestionsRequest);
    }

    @GetMapping("/streets")
    public Iterable<String> getStreets(@RequestBody NewSquadSuggestionsRequest newSquadSuggestionsRequest){
        return placeManager.findStreetsByInput(newSquadSuggestionsRequest);
    }

    @GetMapping("/names")
    public Iterable<String> getPlaceNames(@RequestBody NewSquadSuggestionsRequest newSquadSuggestionsRequest){
        return placeManager.findNamesByInput(newSquadSuggestionsRequest);
    }
/*


    @GetMapping("/cities")
    public Iterable<String> getCities(@RequestParam String city){
        return placeManager.findCitiesByInput(city);
    }
*/


    @PostMapping
    public Place addPlace(@RequestBody Place place){
        return placeManager.save(place);
    }

    @PutMapping
    public Place editPlace(@RequestBody Place place){
        return placeManager.save(place);
    }

    @DeleteMapping
    public void deletePlace(@RequestParam Long id){
        placeManager.deleteById(id);
    }



}
