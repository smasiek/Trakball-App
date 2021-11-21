package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

public class NewSquadRequest {

    @NotBlank
    @Size(min = 2, max = 200)
    private String city;

    @NotBlank
    @Size(min = 2, max = 200)
    private String street;

    @NotBlank
    @Size(min = 2, max = 200)
    private String place;

    @NotBlank
    @Size(min = 2, max = 200)
    private String sport;

    private int maxMembers;

    private Double fee;

    private Timestamp date;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}