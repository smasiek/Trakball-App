package com.momot.trakball.repository;

import com.momot.trakball.dao.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends CrudRepository<Place,Long> {



}
