package com.example.brainstromai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class User {

    @Id
    public Long id;
    public String password;

    @OneToMany
    public List<Project> projects;

}
