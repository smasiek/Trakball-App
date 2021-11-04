package com.momot.trakball.repository;

import com.momot.trakball.dao.PlaceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRequestRepository extends JpaRepository<PlaceRequest, Long> {
}