package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.repository.PlaceRepository;
import com.momot.trakball.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceManager {

    private PlaceRepository placeRepository;

    @Autowired
    public PlaceManager(PlaceRepository placeRepository){
        this.placeRepository=placeRepository;
    }

    public Optional<Place> findById(Long id){
        return placeRepository.findById(id);
    }

    public Iterable<Place> findAll(){
        return placeRepository.findAll();
    }

    public Place save(Place place){
        return placeRepository.save(place);
    }

    public void deleteById(Long id){
        placeRepository.deleteById(id);
    }
}
