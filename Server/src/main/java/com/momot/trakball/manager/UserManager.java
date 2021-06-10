package com.momot.trakball.manager;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.repository.UserRepository;
import com.momot.trakball.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManager {

    private UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
    
    public Optional<User> getUserFromContext(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername());
    }

}
