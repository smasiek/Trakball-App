package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.request.DeleteRequest;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.security.jwt.JwtUtils;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Squad> findAll(){

        Optional<List<Squad>> squads = squadRepository.findWithDateAfterToday();
        List<Squad> resultSquads=new ArrayList<>();
        if(squads.isPresent()){
            resultSquads=squads.get().stream().filter(s-> s.getMembers().size()<s.getMaxMembers()).collect(Collectors.toList());
        }
        return resultSquads;
    }

    public Iterable<Squad> findByMember(){
        return userManager.getUserFromContext().isPresent() ?
                userManager.getUserFromContext().get().getSquads() : new ArrayList<>();
    }

    public Optional<Iterable<Squad>> findByPlace(Long id){
        Optional<Place> place = placeManager.findById(id);
        if(place.isPresent()) {
            return squadRepository.findByPlace(place.get());
        }

        Optional<Iterable<Squad>> optional = Optional.empty();
        return Optional.empty();

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

    public ResponseEntity<?> deleteById(DeleteRequest deleteRequest){
        if(squadRepository.existsById(deleteRequest.getId())) {
            squadRepository.deleteById(deleteRequest.getId());
            return ResponseEntity.ok("Squad deleted");
        }
        return ResponseEntity.badRequest().body("Squad not found 🤐");
    }

    public ResponseEntity<?> updateSquad(Long id,SquadsUpdate updateType) {
        Optional<User> user=userManager.getUserFromContext();
        Optional<Squad> squad=findById(id);

        if(checkUserAndSquadEmpty(user,squad))
            return ResponseEntity.badRequest().body(new MessageResponse("No user in context or squad doesn't exist!"));

        Set<Squad> newSquads = user.get().getSquads();

        switch(updateType){
            case LEAVE: {
                if(newSquads.contains(squad.get())) {
                    squad.ifPresent(newSquads::remove);
                    updateUserSquads(user,newSquads);
                    return ResponseEntity.ok(new MessageResponse("You've left squad!"));
                }
                return ResponseEntity.badRequest().body(new MessageResponse("You can't leave this squad! You are not a member"));
            }
            case JOIN:{
                if(newSquads.contains(squad.get())){
                    return ResponseEntity.badRequest().body(new MessageResponse("You are in this squad already!"));
                } else {
                    if(squad.get().getMembers().size()>=squad.get().getMaxMembers())
                        return ResponseEntity.badRequest().body(new MessageResponse("Squad is full"));

                    squad.ifPresent(newSquads::add);
                    updateUserSquads(user,newSquads);
                    return ResponseEntity.ok(new MessageResponse("You've joined squad!"));
                }
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Bad update type"));
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
