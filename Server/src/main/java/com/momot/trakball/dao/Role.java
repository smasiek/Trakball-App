package com.momot.trakball.dao;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    private ERole name;

    public Role(Long role_id, ERole name) {
        this.role_id = role_id;
        this.name = name;
    }

    public Role(ERole roleAdmin) {
        this.name = roleAdmin;
    }

    public Role() {
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long id) {
        this.role_id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name == role.name;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}