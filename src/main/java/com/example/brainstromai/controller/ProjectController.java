package com.example.brainstromai.controller;

import com.example.brainstromai.model.db.Project;
import com.example.brainstromai.model.db.ProjectRole;
import com.example.brainstromai.model.db.User;
import com.example.brainstromai.model.request.CreateProjectRequest;
import com.example.brainstromai.model.response.ApiResponse;
import com.example.brainstromai.model.response.ApiResponseErrorCode;
import com.example.brainstromai.repository.ProjectRepository;
import com.example.brainstromai.repository.ProjectRoleRepository;
import com.example.brainstromai.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectRoleRepository projectRoleRepository;

    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository, ProjectRoleRepository projectRoleRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectRoleRepository = projectRoleRepository;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> createProject(@RequestBody CreateProjectRequest createProjectRequest) {

        Optional<User> userOptional = userRepository.findById(createProjectRequest.username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.fail("invalid user", ApiResponseErrorCode.CreateProjectFail));
        } else {
            User user = userOptional.get();
            Project project = new Project();
            project.projectName = createProjectRequest.projectName;
            project.projectPrompt = createProjectRequest.projectPrompt;
            project.projectRoles = createProjectRequest.projectRoles.stream().map(role -> {
                ProjectRole projectRole = new ProjectRole();
                projectRole.roleName = role.roleName;
                projectRole.roleFocusArea = role.roleFocusArea;
                projectRole.rolePrompt = role.rolePrompt;
                projectRoleRepository.save(projectRole);

                return projectRole;
            }).collect(Collectors.toList());
            project.projectPrompt = createProjectRequest.projectPrompt;
            projectRepository.save(project);
            user.projects.add(project);
            userRepository.save(user);
            return new ResponseEntity<>(ApiResponse.success(project), HttpStatus.CREATED);
        }
    }


    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<List<Project>>> listProjects(@RequestParam String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.fail(new ArrayList<>(), ApiResponseErrorCode.InvalidUser));
        } else {
            User user = userOptional.get();
            return ResponseEntity.ok(ApiResponse.success(user.projects));
        }
    }


}
