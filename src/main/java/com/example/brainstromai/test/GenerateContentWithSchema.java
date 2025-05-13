package com.example.brainstromai.test;


import com.example.brainstromai.geminiConfig.KeywordsGenerateConfigBuilder;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class GenerateContentWithSchema {
    public static void main(String[] args) {
        Client client = Client.builder()
                .apiKey("AIzaSyA3-w4t2gKhLqF808Zw-GlSLw9dfUEAyG8")
                .build();


        GenerateContentConfig config = KeywordsGenerateConfigBuilder.build();

        GenerateContentResponse response =
                client.models.generateContent("gemini-2.0-flash-001",
                        "我想头脑风暴如何推广AI工具，你需要使用的角色有（1.工程师，2.销售，3.主管的角色） 返回给我idea的keyword，以及desc", config);

        System.out.println("Response: " + response.text());
    }
}
