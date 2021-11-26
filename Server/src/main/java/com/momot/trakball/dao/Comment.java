package com.momot.trakball.dao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
public class Comment implements Comparable<Comment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String text;

    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    public Comment() {
    }

    public Comment(String text, Timestamp date, User creator, Squad squad) {
        this.text = text;
        this.date = date;
        this.creator = creator;
        this.squad = squad;
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    @Override
    public int compareTo(Comment o) {
        return date.compareTo(o.date);
    }
}