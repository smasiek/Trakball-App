package com.momot.trakball.repository;

import com.momot.trakball.dao.User;
import com.momot.trakball.dao.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {

}
