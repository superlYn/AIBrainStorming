package com.example.brainstromai.geminiConfig;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Schema;

public class KeywordsGenerateConfigBuilder {


    public static GenerateContentConfig build() {
        return GenerateContentConfig.builder()
                .responseMimeType("application/json")
                .candidateCount(1)
                .responseSchema(buildSchema())
                .build();
    }

    private static Schema buildSchema() {
        Schema idea = Schema.builder()
                .type("object")
                .properties(ImmutableMap.of(
                        "keyword", Schema.builder().type("string").build(),
                        "keywordDesc", Schema.builder().type("string").build()))
                .required(ImmutableList.of("keyword", "keywordDesc"))
                .build();


        Schema ideaWithRole = Schema.builder().type("object").properties(
                ImmutableMap.of(
                        "role", Schema.builder().type("string").description("the role type of the ideas").build(),
                        "keywords", Schema.builder().type("array").items(idea).build()
                )
        ).build();


        return Schema.builder()
                .type("array")
                .items(ideaWithRole)
                .build();
    }


}
