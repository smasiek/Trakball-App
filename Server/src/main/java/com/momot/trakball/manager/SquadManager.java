package com.momot.trakball.manager;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SquadManager {

    private SquadRepository squadRepository;

    @Autowired
    public SquadManager(SquadRepository squadRepository){
        this.squadRepository=squadRepository;
    }

    public Optional<Squad> findById(Long id){
        return squadRepository.findById(id);
    }

    public Iterable<Squad> findAll(){
        return squadRepository.findAll();
    }

    public Squad save(Squad squad){
        return squadRepository.save(squad);
    }

    public void deleteById(Long id){
        squadRepository.deleteById(id);
    }
}
