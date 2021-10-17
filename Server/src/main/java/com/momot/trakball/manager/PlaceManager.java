package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.PlaceRepository;
import com.momot.trakball.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaceManager {

    private final PlaceRepository placeRepository;
    private final SearchRepository searchRepository;

    private final UserManager userManager;

    @Autowired
    public PlaceManager(PlaceRepository placeRepository, SearchRepository searchRepository, UserManager userManager) {
        this.placeRepository = placeRepository;
        this.searchRepository = searchRepository;
        this.userManager = userManager;
    }

    public PlaceDto findById(Long id) {
        PlaceDto placeDto = new PlaceDto();
        placeRepository.findById(id).ifPresent(place -> {
            placeDto.setPlace_id(place.getId());
            placeDto.setName(place.getName());
            placeDto.setCity(place.getCity());
            placeDto.setPostal_code(place.getPostal_code());
            placeDto.setStreet(place.getStreet());
            placeDto.setLatitude(place.getLatitude());
            placeDto.setLongitude(place.getLongitude());
            placeDto.setPhoto(place.getPhoto());
        });
        return placeDto;
    }

    public Optional<Place> findByNameAndStreetAndCity(String name, String street, String city) {
        return placeRepository.findPlaceByNameAndStreetAndCity(name, street, city);
    }

    public Iterable<PlaceDto> findAll() {
        return placeRepository.findAll().stream().map(place -> new PlaceDto(place.getId(), place.getName(), place.getCity(), place.getPostal_code(),
                place.getStreet(), place.getLatitude(), place.getLongitude(), place.getPhoto())).collect(Collectors.toList());
    }

    public Iterable<String> findCitiesByInput(String city, String street, String place) {
        if (!city.equals("") && !street.equals("") && !place.equals("")) {
            return searchRepository.findCitiesWithStreetAndPlace(city,
                    street, place);
        }
        if (!city.equals("") && !place.equals("")) {
            return searchRepository.findCitiesWithPlace(city, place);
        }
        if (!city.equals("") && !street.equals("")) {
            return searchRepository.findCitiesWithStreet(city, street);
        }
        return searchRepository.findCities(city);
    }

    public Iterable<String> findStreetsByInput(String city, String street, String place) {
        if (!city.equals("") && !street.equals("") && !place.equals("")) {
            return searchRepository.findStreetsWithCityAndPlace(street, city, place);
        }
        if (!street.equals("") && !place.equals("")) {
            return searchRepository.findStreetsWithPlace(street, place);
        }
        if (!street.equals("") && !city.equals("")) {
            return searchRepository.findStreetsWithCity(street, city);
        }
        return searchRepository.findStreets(street);
    }

    public Iterable<String> findNamesByInput(String city, String street, String place) {
        if (!city.equals("") && !street.equals("") && !place.equals("")) {
            return searchRepository.findPlacesWithCityAndStreet(place, city, street);
        }
        if (!place.equals("") && !city.equals("")) {
            return searchRepository.findPlacesWithCity(place, city);
        }
        if (!place.equals("") && !street.equals("")) {
            return searchRepository.findPlacesWithStreet(place, street);
        }
        return searchRepository.findPlaces(place);
    }

    public Place save(Place place) {
        return placeRepository.save(place);
    }

    public void deleteById(Long id) {
        placeRepository.deleteById(id);
    }

    public ResponseEntity<?> followPlace(Long place_id, PlaceFollow action) {
        Optional<User> user = userManager.getUserFromContext();
        Optional<Place> place = placeRepository.findById(place_id);

        if (checkUserAndPlace(user, place)) {
            return ResponseEntity.badRequest().body(new MessageResponse("No user in context or place doesn't exist!"));
        }

        Set<Place> places = user.get().getYourPlaces();

        switch (action) {
            case UNFOLLOW: {
                if (places.contains(place.get())) {
                    place.ifPresent(places::remove);
                    updateUsersPlaces(user, places);
                    return ResponseEntity.ok(new MessageResponse("You've unfollowed this place!"));
                }
                return ResponseEntity.badRequest().body(new MessageResponse("You can't leave this squad! You are not a member"));
            }
            case FOLLOW: {
                if (places.contains(place.get())) {
                    return ResponseEntity.badRequest().body(new MessageResponse("You are following this place already!"));
                } else {
                    place.ifPresent(places::add);
                    updateUsersPlaces(user, places);
                    return ResponseEntity.ok(new MessageResponse("You've followed place!"));
                }
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Bad action on place"));
    }

    private boolean checkUserAndPlace(Optional<User> user, Optional<Place> place) {
        return user.isEmpty() || place.isEmpty();
    }

    private void updateUsersPlaces(Optional<User> user, Set<Place> place) {
        user.ifPresent(u -> u.setYourPlaces(place));
        user.ifPresent(userManager::save);
    }
}