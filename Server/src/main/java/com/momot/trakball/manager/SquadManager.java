package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.request.DeleteRequest;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    private boolean isSquadValid(Squad squad){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = dateFormat.parse(squad.getDate());
            Date currentDate= new Date();

            long diffInMillies = date.getTime() - currentDate.getTime();
            return (diffInMillies>0);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Iterable<Squad> findByMember(){
        return userManager.getUserFromContext().isPresent() ?
                userManager.getUserFromContext().get().getSquads().stream().filter(this::isSquadValid).collect(Collectors.toList()) : new ArrayList<>();
    }

    public Optional<Iterable<Squad>> findByPlace(Long id){
        Optional<Place> place = placeManager.findById(id);
        if(place.isPresent()) {
            return squadRepository.findByPlace(place.get());
        }

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

    private boolean checkUserAndSquadEmpty(Optional<User> user, Optional<Squad> squad){
        return user.isEmpty() || squad.isEmpty();
    }

    private void updateUserSquads(Optional<User> user,Set<Squad> newSquads){
        user.ifPresent(u -> u.setSquads(newSquads));
        user.ifPresent(userManager::save);
    }
}
