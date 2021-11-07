package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;

public class ApprovePlaceRequest {

    @NotBlank
    private Long placeRequestId;

    public Long getPlaceRequestId() {
        return placeRequestId;
    }

    public void setPlaceRequestId(Long placeRequestId) {
        this.placeRequestId = placeRequestId;
    }
}
