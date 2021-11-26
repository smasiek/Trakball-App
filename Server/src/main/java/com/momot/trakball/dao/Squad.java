package com.momot.trakball.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "squads")
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long squad_id;

    @NotBlank(message = "Sport can't be blank")
    @Size(min = 2, max = 200, message = "Type valid sport")
    private String sport;

    @Column(name = "max_members")
    private int maxMembers;

    private Double fee;

    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "squad")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "squads")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<User> members = new HashSet<>();

    private String password;

    private boolean secured;

    public Squad(Long squad_id, String sport, int maxMembers, Double fee, Timestamp date, boolean secured, String password,
                 User creator, Place place) {
        this.squad_id = squad_id;
        this.sport = sport;
        this.maxMembers = maxMembers;
        this.fee = fee;
        this.date = date;
        this.secured = secured;
        this.password = password;
        this.creator = creator;
        this.place = place;
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

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }
}