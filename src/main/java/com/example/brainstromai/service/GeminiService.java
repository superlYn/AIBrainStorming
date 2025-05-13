package com.example.brainstromai.service;

import com.example.brainstromai.geminiConfig.KeywordRefreshConfigBuilder;
import com.example.brainstromai.geminiConfig.KeywordsGenerateConfigBuilder;
import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    public static String MODEL_VERSION = "gemini-2.0-flash-001";
    private final Client client;

    public GeminiService(Client client) {
        this.client = client;
    }

    /**
     * 同步生成：直接返回完整文本
     */
    public String generateKeywords() {

        GenerateContentResponse response =
                client.models.generateContent(MODEL_VERSION,
                        "我想头脑风暴如何推广AI工具，你需要使用的角色有（1.工程师，2.销售，3.主管的角色） 返回给我idea的keyword，以及desc",
                        KeywordsGenerateConfigBuilder.build());

        return response.text();
    }


    public String refreshKeyword(String prompt) {
        GenerateContentResponse response = client.models.generateContent(MODEL_VERSION,
                prompt + "返回的结果要简短精炼不超过50个字",
                KeywordRefreshConfigBuilder.build());

        return response.text();
    }

    /**
     * 流式生成：返回一个可迭代的 ResponseStream
     */
    public ResponseStream<GenerateContentResponse> generateStream(String model, String prompt) {
        // 同样，第三个参数传 null 即可
        return client.models.generateContentStream(model, prompt, null);
    }
}
