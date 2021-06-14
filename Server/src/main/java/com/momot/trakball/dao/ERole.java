package com.momot.trakball.dao;

public enum ERole {
    ROLE_USER("ROLE_USER"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String text;
    ERole (String text){
        this.text=text;
    }
    public String getText() {
        return text;
    }

    }
