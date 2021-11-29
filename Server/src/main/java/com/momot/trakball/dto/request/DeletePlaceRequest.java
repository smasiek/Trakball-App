package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;

public class DeletePlaceRequest {

    @NotBlank
    private Long placeId;

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeRequestId) {
        this.placeId = placeRequestId;
    }
}