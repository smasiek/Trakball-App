package com.momot.trakball.dto;

public class UserDto {

    private Long user_id;
    private String name;
    private String surname;
    private String phone;

    public UserDto(Long user_id, String name, String surname, String phone) {
        this.user_id = user_id;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}