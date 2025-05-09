package com.example.brainstromai.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Project {

    @Id
    public Long projectId;

    public String projectName;
    public String projectPrompt;

    @OneToMany
    public List<ProjectRole> projectRoles;

    @OneToOne
    public ProjectReport projectReport;

}





