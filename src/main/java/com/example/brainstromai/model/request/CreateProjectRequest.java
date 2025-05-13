package com.example.brainstromai.model.request;

import java.util.List;

public class CreateProjectRequest {


    public String username;
    public String projectName;
    public String projectPrompt;
    public List<Role> projectRoles;

    public static class Role {
        public String roleName;
        public String roleFocusArea = "";
        public String rolePrompt;
    }
}


