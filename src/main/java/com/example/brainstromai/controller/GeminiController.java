package com.example.brainstromai.controller;

import autovalue.shaded.com.google.common.base.Function;
import com.example.brainstromai.model.db.Keyword;
import com.example.brainstromai.model.db.Project;
import com.example.brainstromai.model.db.ProjectRole;
import com.example.brainstromai.model.response.ApiResponse;
import com.example.brainstromai.model.response.ApiResponseErrorCode;
import com.example.brainstromai.service.GeminiService;
import com.example.brainstromai.service.KeywordService;
import com.example.brainstromai.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/ai")

public class GeminiController {

    private final GeminiService geminiService;
    private final KeywordService keywordService;
    private final ProjectService projectService;

    public GeminiController(GeminiService geminiService, KeywordService keywordService, ProjectService projectService) {
        this.geminiService = geminiService;
        this.keywordService = keywordService;
        this.projectService = projectService;
    }

    @PostMapping(value = "/genIdeas")
    public ResponseEntity<ApiResponse<String>> genIdeas(@RequestParam long projectId) {
        try {
            Project project = projectService.findById(projectId).orElseThrow(() -> new IllegalStateException("Project not found"));
            String rolesStr = project.projectRoles.stream()
                    .map((Function<ProjectRole, String>) role -> role.roleName).collect(Collectors.joining("/"));
            String resp = geminiService.generateKeywords(rolesStr);
            keywordService.saveKeywordsToProject(project, resp);
            return ResponseEntity.ok(ApiResponse.success(resp));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.fail(null, ApiResponseErrorCode.InvalidId, e.getMessage()));
        }
    }


    @PostMapping(value = "/refreshKeyword")
    public ResponseEntity<ApiResponse<Keyword>> refresh(@RequestParam long keywordId,
                                                        @RequestParam(required = false) String userPrompt) {
        try {
            Keyword oldKeyword = keywordService.findKeyword(keywordId).orElseThrow(() -> new IllegalStateException("Keyword not found"));
            String refreshOnlyPrompt = "请基于关键词:" + oldKeyword.keyword + "思考出和" + oldKeyword.keywordDesc + "不重复的点子";

            boolean refreshWithUserPrompt = StringUtils.hasText(userPrompt);
            String finalPrompt = refreshWithUserPrompt ? userPrompt : refreshOnlyPrompt;
            if (refreshWithUserPrompt) {
                oldKeyword.keywordUserPrompt = userPrompt;
            }
            Keyword newKeyword = keywordService.refreshKeywords(oldKeyword, geminiService.refreshKeyword(finalPrompt));
            return ResponseEntity.ok(ApiResponse.success(newKeyword));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.fail(null, ApiResponseErrorCode.InvalidId, e.getMessage()));
        }

    }


}
