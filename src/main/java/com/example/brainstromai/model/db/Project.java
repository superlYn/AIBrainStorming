package com.example.brainstromai.model.db;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long projectId;

    public String projectName;
    public String projectPrompt;

    @OneToMany
    public List<ProjectRole> projectRoles = new ArrayList<>();

    @OneToOne
    public ProjectReport projectReport;


}





