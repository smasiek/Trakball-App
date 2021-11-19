package com.momot.trakball.repository;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {

    @Query("select s from Squad s " +
            "where s.date >= CURRENT_TIMESTAMP " +
            "and s.place = :place")
    Optional<Iterable<Squad>> findByPlace(@Param("place") Place place);

    @Query("select s from Squad s " +
            "where s.date >= CURRENT_TIMESTAMP")
    Optional<List<Squad>> findWithDateAfterToday();

}
