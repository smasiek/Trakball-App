package com.momot.trakball.dto;

import com.momot.trakball.dao.Place;
import com.momot.trakball.dao.User;

import java.sql.Timestamp;

public class SquadDto {
    private Long squad_id;
    private String sport;
    private int currentMembers;
    private int maxMembers;
    private Double fee;
    private Timestamp date;
    private UserDto creator;
    private PlaceDto place;

    public SquadDto() {
    }

    public SquadDto(Long squad_id, String sport, int currentMembers, int maxMembers, Double fee, Timestamp date, User creator, Place place) {
        this.squad_id = squad_id;
        this.sport = sport;
        this.currentMembers = currentMembers;
        this.maxMembers = maxMembers;
        this.fee = fee;
        this.date = date;
        this.creator = new UserDto(creator.getUserId(), creator.getName(), creator.getSurname(), creator.getEmail(), creator.getPhone(), creator.getPhoto());
        this.place = new PlaceDto(place.getPlace_id(), place.getName(), place.getCity(), place.getPostal_code(),
                place.getStreet(), place.getLatitude(), place.getLongitude(), place.getPhoto());
    }

    public Long getSquad_id() {
        return squad_id;
    }

    public void setSquad_id(Long squad_id) {
        this.squad_id = squad_id;
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

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = new UserDto(creator.getUserId(), creator.getName(), creator.getSurname(), creator.getEmail(), creator.getPhone(), creator.getPhoto());
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }

    public PlaceDto getPlace() {
        return place;
    }

    public void setPlace(PlaceDto place) {
        this.place = place;
    }

    public void setPlace(Place place) {
        this.place = new PlaceDto(place.getPlace_id(), place.getName(), place.getCity(), place.getPostal_code(),
                place.getStreet(), place.getLatitude(), place.getLongitude(), place.getPhoto());
    }

    public int getCurrentMembers() {
        return currentMembers;
    }

    public void setCurrentMembers(int currentMembers) {
        this.currentMembers = currentMembers;
    }
}