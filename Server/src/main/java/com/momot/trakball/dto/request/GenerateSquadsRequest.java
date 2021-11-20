package com.momot.trakball.dto.request;

import java.util.List;

public class GenerateSquadsRequest {
    private List<NewSquadRequest> squadRequests;

    public List<NewSquadRequest> getSquadRequests() {
        return squadRequests;
    }

    public void setSquadRequests(List<NewSquadRequest> squadRequests) {
        this.squadRequests = squadRequests;
    }
}