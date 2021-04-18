package com.momot.trakball.dao;


import javax.persistence.*;

@Entity
@Table(name = "squads")
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int creatorID;
    private String creatorName;
    private String sport;
    private int maxMembers;
    private String fee;
    private String placeName;
    private int placeID;
    private String address;
    private String date;

    public Squad(Long id, int creatorID, String creatorName, String sport, int maxMembers, String fee, String placeName, int placeID, String address, String date) {
        this.id = id;
        this.creatorID = creatorID;
        this.creatorName = creatorName;
        this.sport = sport;
        this.maxMembers = maxMembers;
        this.fee = fee;
        this.placeName = placeName;
        this.placeID = placeID;
        this.address = address;
        this.date = date;
    }

    public Squad() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
