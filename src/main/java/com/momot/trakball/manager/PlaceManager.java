package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceManager {

    private List<Place> placeList;

    public PlaceManager(){
        this.placeList =new ArrayList<>();
        placeList.add(new Place(1,"Hala Kamienna","Kraków","30-100","Kamienna 9",10.10013,15.12313,"kamienna.jpg"));
        placeList.add(new Place(2,"Com Com Zone","Kraków","30-101","Krupnicza 11",13.10013,11.12313,"comcom.jpg"));

    }

    public List<Place> getPlaceList() {
        return placeList;
    }

}
