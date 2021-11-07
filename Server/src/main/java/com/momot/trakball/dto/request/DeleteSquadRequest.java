package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;

public class DeleteSquadRequest {

    @NotBlank
    private Long squad_id;

    public Long getId() {
        return squad_id;
    }

    public void setId(Long id) {
        this.squad_id = id;
    }
}
