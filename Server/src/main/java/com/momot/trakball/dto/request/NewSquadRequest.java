package com.momot.trakball.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class NewSquadRequest {

    @NotBlank
    @Size(min = 3, max = 200)
    private String address;

    @NotBlank
    @Size(min=2,max = 200)
    private String date;

    @NotBlank
    @Size(min=2,max = 200)
    private String fee;

    @NotBlank
    @Size(min=2,max = 200)
    private String maxMembers;

    @NotBlank
    @Size(min=2,max = 200)
    private String placeName;

    @NotBlank
    @Size(min=2,max = 200)
    private String sport;

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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(String maxMembers) {
        this.maxMembers = maxMembers;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
