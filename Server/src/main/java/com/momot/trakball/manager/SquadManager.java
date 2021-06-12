package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static com.momot.trakball.manager.SquadsUpdate.*;

@Service
public class SquadManager {

    private final SquadRepository squadRepository;

    private final UserManager userManager;

    private final PlaceManager placeManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    public SquadManager(SquadRepository squadRepository,UserManager userManager,PlaceManager placeManager){
        this.squadRepository=squadRepository;
        this.userManager=userManager;
        this.placeManager=placeManager;
    }

    public Optional<Squad> findById(Long id){
        return squadRepository.findById(id);
    }

    public Iterable<Squad> findAll(){
        return squadRepository.findAll();
    }

    public Iterable<Squad> findByMember(){
        return userManager.getUserFromContext().isPresent() ?
                userManager.getUserFromContext().get().getSquads() : new ArrayList<>();
    }

    public Squad addSquad(NewSquadRequest newSquadRequestSquad){
        Optional<User> creator=userManager.getUserFromContext();
        Optional<Place> place=placeManager.findByNameAndStreetAndCity(newSquadRequestSquad.getPlace(),
                newSquadRequestSquad.getStreet(), newSquadRequestSquad.getCity());

        Squad squad=new Squad(null,newSquadRequestSquad.getSport(), newSquadRequestSquad.getMaxMembers(),
                newSquadRequestSquad.getFee(), newSquadRequestSquad.getDate(), creator, place);

        return squadRepository.save(squad);
    }

//    public Squad addYourSquad()

    public Squad save(Squad squad){
        return squadRepository.save(squad);
    }

    public void deleteById(Long id){
        squadRepository.deleteById(id);
    }

    public ResponseEntity<?> updateSquad(Long id,SquadsUpdate updateType) {
        Optional<User> user=userManager.getUserFromContext();
        Optional<Squad> squad=findById(id);

        if(checkUserAndSquadEmpty(user,squad))
            return ResponseEntity.badRequest().body(new MessageResponse("No user in context or squad doesn't exist!"));

        Set<Squad> newSquads = user.get().getSquads();

        if(newSquads.contains(squad.get())) {
            if (updateType == JOIN)
                return ResponseEntity.badRequest().body(new MessageResponse("You are in this squad already!"));
            else {
                squad.ifPresent(newSquads::remove);
                updateUserSquads(user,newSquads);
                return ResponseEntity.ok(new MessageResponse("You've left squad!"));
            }
        }
        else
            if (updateType==LEAVE)
                return ResponseEntity.badRequest().body(new MessageResponse("You can't leave this squad! You are not a member"));
            else {
                squad.ifPresent(newSquads::add);
                updateUserSquads(user,newSquads);
                return ResponseEntity.ok(new MessageResponse("You've joined squad!"));
            }
    }

/*

    public ResponseEntity<?> joinSquad(Long id) {
        Optional<User> user=userManager.getUserFromContext();
        Optional<Squad> squad=findById(id);

        if(checkUserAndSquadEmpty(user,squad))
            return ResponseEntity.badRequest().body(new MessageResponse("No user in context or squad doesn't exist!"));

        Set<Squad> newSquads = user.get().getSquads();

        if(newSquads.contains(squad.get()))
            return ResponseEntity.badRequest().body(new MessageResponse("You are in this squad already!"));

        squad.ifPresent(newSquads::add);
        updateUserSquads(user,newSquads);
        return ResponseEntity.ok(new MessageResponse("You've joined squad!"));
    }


public ResponseEntity<?> leaveSquad(Long id) {

        Optional<User> user=userManager.getUserFromContext();
        Optional<Squad> squad=findById(id);

        if(checkUserAndSquadEmpty(user,squad))
            return ResponseEntity.badRequest().body(new MessageResponse("No user in context or squad doesn't exist!"));

        Set<Squad> newSquads = user.get().getSquads();

        if(newSquads.contains(squad.get()))
            return ResponseEntity.badRequest().body(new MessageResponse("You can't leave this squad! You are not a member"));

        updateUserSquads(user,newSquads);
        return ResponseEntity.ok(new MessageResponse("You've left squad!"));
    }*/

    private boolean checkUserAndSquadEmpty(Optional<User> user, Optional<Squad> squad){
        return user.isEmpty() || squad.isEmpty();
    }

    private void updateUserSquads(Optional<User> user,Set<Squad> newSquads){
        Set<Squad> finalNewSquads = newSquads;
        user.ifPresent(u -> u.setSquads(finalNewSquads));
        user.ifPresent(userManager::save);
    }

    /*public Long getIdFromToken(String token){
        jwtUtils.validateJwtToken(token);
        return Long.parseLong(jwtUtils.getIdFromJwtToken(token));
    }*/
}
