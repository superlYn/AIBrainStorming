package com.example.brainstromai.model.db;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long roleId;
    public String roleName;
    public String roleFocusArea;
    public String rolePrompt;

    @OneToMany
    public List<Keyword> keywords = new ArrayList<>();
}
