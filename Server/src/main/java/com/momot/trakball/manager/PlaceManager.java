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

    public Iterable<String> findCitiesByInput(String city,String street,String place){
        if(!city.equals("") && !street.equals("") && !place.equals("")){
            return searchRepository.findCitiesWithStreetAndPlace(city,
                    street,place);
        }
        if(!city.equals("") && !place.equals("")){
            return searchRepository.findCitiesWithPlace(city,place);
        }
        if(!city.equals("") && !street.equals("")){
            return searchRepository.findCitiesWithStreet(city,street);
        }
        return searchRepository.findCities(city);

    }

    public Iterable<String> findStreetsByInput(String city,String street,String place){
        if(!city.equals("") && !street.equals("") && !place.equals("")){
            return searchRepository.findStreetsWithCityAndPlace(street,city,place);
        }
        if(!street.equals("") && !place.equals("")){
            return searchRepository.findStreetsWithPlace(street,place);
        }
        if(!street.equals("")&& !city.equals("")){
            return searchRepository.findStreetsWithCity(street,city);
        }
        return searchRepository.findStreets(street);

    }

    public Iterable<String> findNamesByInput(String city,String street,String place){
        if(!city.equals("") && !street.equals("") && !place.equals("")){
            return searchRepository.findPlacesWithCityAndStreet(place,city,street);
        }
        if(!place.equals("")&& !city.equals("")){
            return searchRepository.findPlacesWithCity(place,city);
        }
        if(!place.equals("")&& !street.equals("")){
            return searchRepository.findPlacesWithStreet(place,street);
        }
        return searchRepository.findPlaces(place);

    }

    public Place save(Place place){
        return placeRepository.save(place);
    }

    public void deleteById(Long id){
        placeRepository.deleteById(id);
    }
}
