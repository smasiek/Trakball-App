package com.momot.trakball.repository;

import com.momot.trakball.dao.ERole;
import com.momot.trakball.dao.Role;
import com.momot.trakball.dao.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {

}
