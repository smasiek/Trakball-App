package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dto.request.NewSquadSuggestionsRequest;
import com.momot.trakball.repository.PlaceRepository;
import com.momot.trakball.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceManager {

    private PlaceRepository placeRepository;
    private SearchRepository searchRepository;

    @Autowired
    public PlaceManager(PlaceRepository placeRepository,SearchRepository searchRepository){
        this.placeRepository=placeRepository;
        this.searchRepository=searchRepository;
    }

    public Optional<Place> findById(Long id){
        return placeRepository.findById(id);
    }

    public Optional<Place> findByNameAndStreetAndCity(String name, String street,String city){
        return placeRepository.findPlaceByNameAndStreetAndCity(name,street,city);
    }

    public Iterable<Place> findAll(){
        return placeRepository.findAll();
    }

    public Iterable<String> findCitiesByInput(NewSquadSuggestionsRequest newSquadSuggestionsRequest){
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getPlace().isPresent() && newSquadSuggestionsRequest.getStreet().isPresent()){
            return searchRepository.findCitiesWithStreetAndPlace(newSquadSuggestionsRequest.getCity().get(),
                    newSquadSuggestionsRequest.getStreet().get(),newSquadSuggestionsRequest.getPlace().get());
        }
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getPlace().isPresent()){
            return searchRepository.findCitiesWithPlace(newSquadSuggestionsRequest.getCity().get(),newSquadSuggestionsRequest.getPlace().get());
        }
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getStreet().isPresent()){
            return searchRepository.findCitiesWithStreet(newSquadSuggestionsRequest.getCity().get(),newSquadSuggestionsRequest.getStreet().get());
        }
        return searchRepository.findCities(newSquadSuggestionsRequest.getCity().get());

    }

    public Iterable<String> findStreetsByInput(NewSquadSuggestionsRequest newSquadSuggestionsRequest){
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getPlace().isPresent() && newSquadSuggestionsRequest.getStreet().isPresent()){
            return searchRepository.findStreetsWithCityAndPlace(newSquadSuggestionsRequest.getStreet().get(),
                    newSquadSuggestionsRequest.getCity().get(),newSquadSuggestionsRequest.getPlace().get());
        }
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getPlace().isPresent()){
            return searchRepository.findStreetsWithPlace(newSquadSuggestionsRequest.getStreet().get(),newSquadSuggestionsRequest.getPlace().get());
        }
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getStreet().isPresent()){
            return searchRepository.findStreetsWithCity(newSquadSuggestionsRequest.getStreet().get(),newSquadSuggestionsRequest.getCity().get());
        }
        return searchRepository.findStreets(newSquadSuggestionsRequest.getStreet().get());

    }

    public Iterable<String> findNamesByInput(NewSquadSuggestionsRequest newSquadSuggestionsRequest){
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getPlace().isPresent() && newSquadSuggestionsRequest.getStreet().isPresent()){
            return searchRepository.findCitiesWithStreetAndPlace(newSquadSuggestionsRequest.getCity().get(),
                    newSquadSuggestionsRequest.getStreet().get(),newSquadSuggestionsRequest.getPlace().get());
        }
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getPlace().isPresent()){
            return searchRepository.findCitiesWithPlace(newSquadSuggestionsRequest.getCity().get(),newSquadSuggestionsRequest.getPlace().get());
        }
        if(newSquadSuggestionsRequest.getCity().isPresent() && newSquadSuggestionsRequest.getStreet().isPresent()){
            return searchRepository.findCitiesWithStreet(newSquadSuggestionsRequest.getCity().get(),newSquadSuggestionsRequest.getStreet().get());
        }
        return searchRepository.findCities(newSquadSuggestionsRequest.getCity().get());

    }

    public Place save(Place place){
        return placeRepository.save(place);
    }

    public void deleteById(Long id){
        placeRepository.deleteById(id);
    }
}
