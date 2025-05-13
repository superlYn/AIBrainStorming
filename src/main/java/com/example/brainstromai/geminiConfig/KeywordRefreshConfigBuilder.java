package com.example.brainstromai.geminiConfig;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Schema;

public class KeywordRefreshConfigBuilder {

    public static GenerateContentConfig build() {
        return GenerateContentConfig.builder()
                .responseMimeType("application/json")
                .candidateCount(1)
                .responseSchema(buildSchema())
                .build();
    }

    private static Schema buildSchema() {
        return Schema.builder()
                .type("object")
                .properties(ImmutableMap.of(
                        "keyword", Schema.builder().type("string").build(),
                        "keywordDesc", Schema.builder().type("string").build()))
                .required(ImmutableList.of("keyword", "keywordDesc"))
                .build();
    }
}
