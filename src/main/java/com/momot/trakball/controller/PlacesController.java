package com.momot.trakball.controller;

import com.momot.trakball.dao.Place;
import com.momot.trakball.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class PlacesController {

    @Autowired
    private PlaceRepository placeRepository;

   /* @GetMapping("/places")
    public List<Place> places(){
        return placeRepository.getPlaceList();
    }*/

}
