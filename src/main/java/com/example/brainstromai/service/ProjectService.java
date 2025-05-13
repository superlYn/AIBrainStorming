package com.example.brainstromai.service;

import com.example.brainstromai.model.db.Project;
import com.example.brainstromai.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<Project> findById(Long projectId) {
        return projectRepository.findById(projectId);
    }
}
