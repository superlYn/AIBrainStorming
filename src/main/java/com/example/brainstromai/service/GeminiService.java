package com.example.brainstromai.service;

import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService(Client client) {
        this.client = client;
    }

    /**
     * 同步生成：直接返回完整文本
     */
    public String generate(String model, String prompt) {
        // 第三个参数是 GenerateContentOptions，可传 null 或自定义
        GenerateContentResponse resp =
                client.models.generateContent(model, prompt, null);
        return resp.text();
    }

    /**
     * 流式生成：返回一个可迭代的 ResponseStream
     */
    public ResponseStream<GenerateContentResponse> generateStream(String model, String prompt) {
        // 同样，第三个参数传 null 即可
        return client.models.generateContentStream(model, prompt, null);
    }
}
