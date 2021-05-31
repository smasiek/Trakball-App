package com.momot.trakball.manager;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.dto.request.NewSquadRequest;
import com.momot.trakball.repository.SquadRepository;
import com.momot.trakball.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SquadManager {

    private SquadRepository squadRepository;

    private UserManager userManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    public SquadManager(SquadRepository squadRepository,UserManager userManager){
        this.squadRepository=squadRepository;
        this.userManager=userManager;
    }

    public Optional<Squad> findById(Long id){
        return squadRepository.findById(id);
    }

    public Iterable<Squad> findAll(){
        return squadRepository.findAll();
    }

    public Iterable<Squad> findByMember(String token){
        Long id=getIdFromToken(token);

        return userManager.findById(id).get().getSquads();
    }

/*    public Squad addSquad(NewSquadRequest newSquadRequestquad, String token){
        Long id=getIdFromToken(token);
        String name,surname;
        //TODO pobranie z userManager imienia i nazwiska jak juz dodam
        Place placeName;
        //J.W. pobranie place name z placeManager.
        Squad squad=new Squad(null,id,name + " " + surname, newSquadRequestquad.getSport(), newSquadRequestquad.getMaxMembers(), newSquadRequestquad.getFee(), placeName.getName(), newSquadRequest.pla);
        return squadRepository.save(squad);
    }*/

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
