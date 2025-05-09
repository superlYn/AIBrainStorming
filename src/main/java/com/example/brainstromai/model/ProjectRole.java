package com.example.brainstromai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class ProjectRole {
    @Id
    public  Long roleId;
    public  String roleName;
    public  String rolePrompt;

    @OneToMany
    public List<ChatRecord> chatRecords;
}
