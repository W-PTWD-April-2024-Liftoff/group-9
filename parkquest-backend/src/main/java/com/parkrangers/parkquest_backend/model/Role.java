package com.parkrangers.parkquest_backend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Roleid;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users;

    public Role(Long Roleid, String name) {
        this.Roleid = Roleid;
        this.name = name;
    }

    public Role() {}

    public Long getRoleid() {
        return Roleid;
    }

    public void setRoleid(Long id) {
        this.Roleid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{name='" + name + "'}";
    }

}