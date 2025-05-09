package com.example.brainstromai.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    public String username;
    public String password;


    @OneToMany
    public List<Project> projects = new ArrayList<Project>();


}
