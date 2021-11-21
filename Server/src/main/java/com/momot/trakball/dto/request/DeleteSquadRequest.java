package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;

public class DeleteSquadRequest {

    @NotBlank
    private Long squad_id;

    public Long getSquad_id() {
        return squad_id;
    }

    public void setSquad_id(Long squad_id) {
        this.squad_id = squad_id;
    }
}
