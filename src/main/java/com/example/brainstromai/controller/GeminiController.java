package com.example.brainstromai.controller;

import com.example.brainstromai.model.db.Keyword;
import com.example.brainstromai.model.response.ApiResponse;
import com.example.brainstromai.model.response.ApiResponseErrorCode;
import com.example.brainstromai.service.GeminiService;
import com.example.brainstromai.service.KeywordService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ai")

public class GeminiController {

    private final GeminiService geminiService;
    private final KeywordService keywordService;

    public GeminiController(GeminiService geminiService, KeywordService keywordService) {
        this.geminiService = geminiService;
        this.keywordService = keywordService;
    }


    @PostMapping(value = "/genIdeas")
    public ResponseEntity<ApiResponse<String>> genIdeas(@RequestParam long projectId) {
        String resp = geminiService.generateKeywords();
        try {
            keywordService.saveKeywordsToProject(projectId, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(ApiResponse.success(resp));
    }


    @PostMapping(value = "/refreshKeyword")
    public ResponseEntity<ApiResponse<Keyword>> refresh(@RequestParam long keywordId,
                                                        @RequestParam(required = false) String userPrompt) {
        try {
            Keyword oldKeyword = keywordService.findKeyword(keywordId).orElseThrow(() -> new RuntimeException("Keyword not found"));
            String refreshOnlyPrompt = "请基于关键词:" + oldKeyword.keyword + "思考出和" + oldKeyword.keywordDesc + "不重复的点子";

            boolean refreshWithUserPrompt = StringUtils.hasText(userPrompt);
            String finalPrompt = refreshWithUserPrompt ? userPrompt : refreshOnlyPrompt;
            if (refreshWithUserPrompt) {
                oldKeyword.keywordUserPrompt = userPrompt;
            }
            Keyword newKeyword = keywordService.refreshKeywords(oldKeyword, geminiService.refreshKeyword(finalPrompt));
            return ResponseEntity.ok(ApiResponse.success(newKeyword));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.fail(null, ApiResponseErrorCode.RuntimeException, e.getMessage()));
        }

    }


}
