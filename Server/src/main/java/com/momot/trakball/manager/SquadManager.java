package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.security.jwt.AuthTokenFilter;
import com.momot.trakball.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SquadManager {


    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

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

    public Iterable<Squad> findByMember(String token){
        Long id=getIdFromToken(token);

        return userManager.findById(id).get().getSquads(); //todo to mozna poprawic zeby bylo bezpiecznie. Poki co moze rzucic nieobsluzony error
    }

    public Squad addSquad(NewSquadRequest newSquadRequestquad, String token){
        Long id=getIdFromToken(token);
        Optional<User> creator=userManager.findById(id);

        Optional<Place> place=placeManager.findByNameAndStreetAndCity(newSquadRequestquad.getPlace(),
                newSquadRequestquad.getStreet(), newSquadRequestquad.getCity());

        Squad squad=new Squad(null,newSquadRequestquad.getSport(), newSquadRequestquad.getMaxMembers(),
                newSquadRequestquad.getFee(), newSquadRequestquad.getDate(), creator, place);
        return squadRepository.save(squad);
    }

//    public Squad addYourSquad()

    public Squad save(Squad squad){
        return squadRepository.save(squad);
    }

    public void deleteById(Long id){
        squadRepository.deleteById(id);
    }

    public Long getIdFromToken(String token){
        jwtUtils.validateJwtToken(token);
        return Long.parseLong(jwtUtils.getIdFromJwtToken(token));
    }
}
