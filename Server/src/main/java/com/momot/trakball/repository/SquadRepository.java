package com.momot.trakball.repository;

import com.momot.trakball.dao.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {



    /*private List<Squad> squadList;

    public SquadRepository(){
        this.squadList=new ArrayList<>();
        squadList.add(new Squad(1,1,"Janek Snow",
                "Piłka nożna",15,"10 zł",
                "Hala Kamienna",1,"Kamienna 9",
                "17:00, 30-11-2020"));
        squadList.add(new Squad(2,1,"Janek Śnieg",
                "Piłka koszykowa",10,"5 zł",
                "Com Com Zone",2,"Krupnicza 11",
                "18:00, 31-11-2020"));
    }

    public List<Squad> getSquadList() {
        return squadList;
    }*/
}
