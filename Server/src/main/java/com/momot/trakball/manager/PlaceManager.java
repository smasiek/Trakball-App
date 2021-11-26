package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.PlaceRequest;
import com.momot.trakball.dao.Role;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.PlaceRequestDto;
import com.momot.trakball.dto.request.ApprovePlaceRequest;
import com.momot.trakball.dto.request.DeletePlaceRequest;
import com.momot.trakball.dto.request.NewPlaceRequest;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.PlaceRepository;
import com.momot.trakball.repository.PlaceRequestRepository;
import com.momot.trakball.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.momot.trakball.dao.ERole.ROLE_ADMIN;
import static com.momot.trakball.dao.ERole.ROLE_MODERATOR;

@Service
public class PlaceManager {
    private final PlaceRepository placeRepository;
    private final PlaceRequestRepository placeRequestRepository;
    private final SearchRepository searchRepository;

    private final UserManager userManager;

    @Autowired
    public PlaceManager(PlaceRepository placeRepository, SearchRepository searchRepository, PlaceRequestRepository placeRequestRepository, UserManager userManager) {
        this.placeRepository = placeRepository;
        this.placeRequestRepository = placeRequestRepository;
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

    public Iterable<PlaceDto> findByCity(String city) {
        return placeRepository.findPlacesByCity(city).stream().map(place -> new PlaceDto(place.getId(), place.getName(), place.getCity(), place.getPostal_code(),
                place.getStreet(), place.getLatitude(), place.getLongitude(), place.getPhoto())).collect(Collectors.toList());
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
        if (!street.equals("") && !city.equals("")) {
            return searchRepository.findPlacesWithCityAndStreetWithoutPlace(city, street);
        }

        return searchRepository.findPlaces(place);
    }

    public Place save(Place place) {
        return placeRepository.save(place);
    }

    public void savePlaceRequest(PlaceRequest place) {
        placeRequestRepository.save(place);
    }

    public void deleteById(Long id) {
        placeRepository.deleteById(id);
    }

    public ResponseEntity<?> followPlace(Long place_id, PlaceFollow action) {
        Optional<User> user = userManager.getUserFromContext();
        Optional<Place> place = placeRepository.findById(place_id);

        if (user.isEmpty() || place.isEmpty()) {
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

    private void updateUsersPlaces(Optional<User> user, Set<Place> place) {
        user.ifPresent(u -> u.setYourPlaces(place));
        user.ifPresent(userManager::save);
    }

    public ResponseEntity<?> addPlace(NewPlaceRequest newPlaceRequest) {

        Optional<Place> existingPlace = placeRepository.findPlaceByLatitudeAndLongitude(newPlaceRequest.getLatitude(), newPlaceRequest.getLongitude());

        if (existingPlace.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Place already exists!"));
        }

        Optional<User> requester = userManager.getUserFromContext();

        if (requester.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Your session expired! Log in once again"));
        }

        if (requester.get().getRoles().contains(new Role(ROLE_ADMIN)) || requester.get().getRoles().contains(new Role(ROLE_MODERATOR))) {
            save(new Place(newPlaceRequest));
            return ResponseEntity.ok(newPlaceRequest);
        }

        savePlaceRequest(new PlaceRequest(newPlaceRequest, requester.get()));
        return ResponseEntity.ok(newPlaceRequest);
    }

    public Iterable<PlaceRequestDto> getPlaceRequests() {
        return placeRequestRepository.findAll().stream().map(place -> new PlaceRequestDto(place.getId(), place.getName(), place.getCity(), place.getPostal_code(),
                place.getStreet(), place.getLatitude(), place.getLongitude(), place.getPhoto(), place.getRequester().getUserId(), place.getRequester().getName() + ' ' + place.getRequester().getSurname())).collect(Collectors.toList());
    }

    public ResponseEntity<?> approvePlaceRequests(ApprovePlaceRequest approvedPlaceId) {
        Optional<PlaceRequest> placeRequest = placeRequestRepository.findById(approvedPlaceId.getPlaceRequestId());
        placeRequest.ifPresent(request -> {
            save(new Place(request));
            placeRequestRepository.deleteById(request.getId());
        });

        return ResponseEntity.ok(new MessageResponse("Place approved! 🙂"));
    }

    public ResponseEntity<?> deletePlaceRequests(DeletePlaceRequest deletedPlaceRequestId) {
        if (placeRequestRepository.existsById(deletedPlaceRequestId.getPlaceRequestId())) {
            placeRequestRepository.deleteById(deletedPlaceRequestId.getPlaceRequestId());
            return ResponseEntity.ok("New place request deleted");
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Request not found. Something went wrong."));
    }
}