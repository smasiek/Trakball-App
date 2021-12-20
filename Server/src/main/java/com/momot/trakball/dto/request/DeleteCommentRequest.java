package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;

public class DeleteCommentRequest {

    @NotBlank
    private Long comment_id;

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }
}
