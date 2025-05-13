package com.example.brainstromai.service;


import com.example.brainstromai.model.db.Keyword;
import com.example.brainstromai.model.db.Project;
import com.example.brainstromai.model.db.ProjectRole;
import com.example.brainstromai.repository.KeywordRepository;
import com.example.brainstromai.repository.ProjectRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KeywordService {

    private final ProjectRepository projectRepository;
    private final KeywordRepository keywordRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public KeywordService(ProjectRepository projectRepository, KeywordRepository keywordRepository) {
        this.projectRepository = projectRepository;
        this.keywordRepository = keywordRepository;
    }

    public void saveKeywordsToProject(Project project, String keywordsResponse) throws Exception {

        List<ProjectRole> projectRoles = project.projectRoles;
        if (projectRoles.isEmpty()) {
            throw new IllegalStateException("invalid project roles, number of projectRoles is 0");
        }


        JsonNode arrayNode = objectMapper.readTree(keywordsResponse);
        for (JsonNode node : arrayNode) {
            String roleName = node.get("role").asText();
            for (ProjectRole projectRole : projectRoles) {
                if (projectRole.roleName.equals(roleName)) {
                    JsonNode keywordsJson = node.get("keywords");
                    projectRole.keywords = parseKeywords(keywordsJson);
                }
            }
        }

        projectRepository.save(project);
    }

    private List<Keyword> parseKeywords(JsonNode keywordsJson) {
        List<Keyword> keywords = new ArrayList<>();
        for (JsonNode keywordJson : keywordsJson) {
            Keyword keyword = new Keyword();
            keyword.keyword = keywordJson.get("keyword").asText();
            keyword.keywordDesc = keywordJson.get("keywordDesc").asText();
            keywordRepository.save(keyword);
            keywords.add(keyword);
        }
        return keywords;
    }


    public Optional<Keyword> findKeyword(long keywordId) {
        return keywordRepository.findById(keywordId);
    }

    public Keyword refreshKeywords(Keyword keyword, String newKeywordJson) throws Exception {
        JsonNode node = objectMapper.readTree(newKeywordJson);

        keyword.keywordDesc = node.get("keywordDesc").asText();
        keywordRepository.save(keyword);
        return keyword;
    }
}
