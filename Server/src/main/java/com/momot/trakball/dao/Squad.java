package com.momot.trakball.dao;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "squads")
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long squad_id;

    @NotBlank
    @Size(min=2,max = 200)
    private String sport;

    @Column(name="max_members")
    private int maxMembers;

    @NotBlank
    @Size(min=0,max = 50)
    private String fee;

    @NotBlank
    @Size(min=2,max = 50)
    private String date;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    //TODO wykminic jak zapobiec cyklowi

    @ManyToOne
    @JoinColumn(name= "place_id")
    @JsonIgnoreProperties(value = "squads",allowSetters = true)
    private Place place;

    @ManyToMany(mappedBy="squads")
    private Set<User> members=new HashSet<>();

    /*@ManyToMany(mappedBy = "squads")
    private Set<User> users = new HashSet<>();*/

    public Squad(Long squad_id, String sport, int maxMembers, String fee, String date,
                 Optional<User> creator, Optional<Place> place) {
        this.squad_id = squad_id;
        this.sport = sport;
        this.maxMembers = maxMembers;
        this.fee = fee;
        this.date = date;
        creator.ifPresent(c -> this.creator = c);
        place.ifPresent(p->this.place=p);
    }

    public Squad() {
    }

    public Long getSquad_id() {
        return squad_id;
    }

    public void setSquad_id(Long id) {
        this.squad_id = id;
    }


    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }


}
