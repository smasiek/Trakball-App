package com.momot.trakball.dto;

import com.momot.trakball.dao.Comment;

import java.sql.Timestamp;

public class CommentDto implements Comparable<CommentDto> {

    private Long comment_id;
    private String text;
    private Timestamp date;
    private Long creator_id;
    private String creator_fullname;
    private String creator_avatar_url;
    private Long squad_id;

    public CommentDto() {
    }

    public CommentDto(String text, Timestamp date) {
        this.text = text;
        this.date = date;
    }

    public CommentDto(Long comment_id, String text, Timestamp date, Long creator_id, Long squad_id) {
        this.comment_id = comment_id;
        this.text = text;
        this.date = date;
        this.creator_id = creator_id;
        this.squad_id = squad_id;
    }

    public CommentDto(Comment comment) {
        this.comment_id = comment.getComment_id();
        this.text = comment.getText();
        this.date = comment.getDate();
        this.creator_id = comment.getCreator().getUserId();
        this.squad_id = comment.getSquad().getSquad_id();
        this.creator_avatar_url = comment.getCreator().getPhoto();
        this.creator_fullname = comment.getCreator().getName() + " " + comment.getCreator().getSurname();
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
    }

    public Long getSquad_id() {
        return squad_id;
    }

    public void setSquad_id(Long squad_id) {
        this.squad_id = squad_id;
    }

    public String getCreator_fullname() {
        return creator_fullname;
    }

    public void setCreator_fullname(String creator_fullname) {
        this.creator_fullname = creator_fullname;
    }

    public String getCreator_avatar_url() {
        return creator_avatar_url;
    }

    public void setCreator_avatar_url(String creator_avatar_url) {
        this.creator_avatar_url = creator_avatar_url;
    }

    @Override
    public int compareTo(CommentDto o) {
        return date.compareTo(o.date);
    }
}