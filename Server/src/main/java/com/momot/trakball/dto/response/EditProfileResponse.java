package com.momot.trakball.dto.response;

import java.util.List;

public class EditProfileResponse {
    private String email;
    private String name;
    private String surname;
    private String phone;

    public EditProfileResponse(String accessToken, Long id, String email, String name, String surname,
                       String phone,  List<String> roles) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

}
