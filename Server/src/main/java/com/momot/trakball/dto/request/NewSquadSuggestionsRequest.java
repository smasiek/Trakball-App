package com.momot.trakball.dto.request;

import java.util.Optional;

public class NewSquadSuggestionsRequest {
    private Optional<String> city = Optional.empty() ;
    private Optional<String> street = Optional.empty();
    private Optional<String> place = Optional.empty();
    //TODO Zapytać czy tak jest git uzywac optional?


    public NewSquadSuggestionsRequest() {
    }

    public NewSquadSuggestionsRequest(Optional<String> city, Optional<String> street, Optional<String> place) {
        this.city = city;
        this.street = street;
        this.place = place;
    }

    public Optional<String> getCity() {
        return city;
    }

    public void setCity(Optional<String> city) {
        this.city = city;
    }

    public Optional<String> getStreet() {
        return street;
    }

    public void setStreet(Optional<String> street) {
        this.street = street;
    }

    public Optional<String> getPlace() {
        return place;
    }

    public void setPlace(Optional<String> place) {
        this.place = place;
    }
}
