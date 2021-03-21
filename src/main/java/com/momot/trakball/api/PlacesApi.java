package com.momot.trakball.api;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.manager.PlaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class PlacesApi {

    @Autowired
    private PlaceManager placeManager;

    @GetMapping("/places")
    public List<Place> places(){
        return placeManager.getPlaceList();
    }

}
