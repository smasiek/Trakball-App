package com.momot.trakball.repository;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {



}
