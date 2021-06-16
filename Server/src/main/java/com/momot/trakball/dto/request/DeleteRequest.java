package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;

public class DeleteRequest {

    @NotBlank
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
