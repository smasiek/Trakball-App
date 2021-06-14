package com.momot.trakball.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.Set;

public class EditProfileRequest {

    @Size(max = 50)
    @Email
    private Optional<String> email=Optional.empty();

    @Size(min = 6, max = 40)
    private Optional<String> password=Optional.empty();

    @Size(max = 40)
    private Optional<String> name=Optional.empty();

    @Size(max = 40)
    private Optional<String> surname=Optional.empty();

    @Size(max = 40)
    private Optional<String> phone=Optional.empty();

    private Optional<Set<String>> role=Optional.empty();

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<String> getSurname() {
        return surname;
    }

    public void setSurname(Optional<String> surname) {
        this.surname = surname;
    }

    public Optional<String> getPhone() {
        return phone;
    }

    public void setPhone(Optional<String> phone) {
        this.phone = phone;
    }

    public Optional<Set<String>> getRole() {
        return role;
    }

    public void setRole(Optional<Set<String>> role) {
        this.role = role;
    }
}
