package com.momot.trakball;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Start {

    private SquadRepository squadRepository;

    @Autowired
    public Start(SquadRepository squadRepository){
        this.squadRepository=squadRepository;
    }


   /* @EventListener(ApplicationReadyEvent.class)
    public void test(){
        Squad squad1 = new Squad(1L,1,"Janek Snow",
                "Piłka nożna",15,"10 zł",
                "Hala Kamienna",1,"Kamienna 9",
                "17:00, 30-11-2020");
        Squad squad2 = new Squad(2L,1,"Janek Śnieg",
                "Piłka koszykowa",10,"5 zł",
                "Com Com Zone",2,"Krupnicza 11",
                "18:00, 31-11-2020");

        squadRepository.save(squad1);
        squadRepository.save(squad2);


    }*/
}
