package com.momot.trakball.repository;

import com.momot.trakball.dao.ERole;
import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {

    Optional<Place> findPlaceByNameAndStreetAndCity(String name, String street,String city);

}
