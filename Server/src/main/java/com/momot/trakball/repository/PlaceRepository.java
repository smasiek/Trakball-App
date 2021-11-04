package com.momot.trakball.repository;

import com.momot.trakball.dao.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findPlaceByNameAndStreetAndCity(String name, String street, String city);

    Optional<Place> findPlaceByLatitudeAndLongitude(double latitude, double longitude);
}
