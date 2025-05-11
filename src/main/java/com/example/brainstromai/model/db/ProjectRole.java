package com.example.brainstromai.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProjectRole {
    @Id
    public Long roleId = System.currentTimeMillis();
    public String roleName;
    public String rolePrompt;

    @OneToMany
    public List<ChatRecord> chatRecords = new ArrayList<>();
}
