package com.example.brainstromai.controller;

import com.example.brainstromai.service.GeminiService;
import com.google.genai.ResponseStream;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * GeminiController 提供两个接口：
 * 1. GET  /       —— 返回服务状态
 * 2. POST /ask    —— 接收 JSON 请求 {model, prompt} 并返回 Gemini 生成结果
 */
@RestController
public class GeminiController {

    private final GeminiService service;

    public GeminiController(GeminiService service) {
        this.service = service;
    }

    /**
     * 服务启动后访问根路径可测试是否就绪：
     *   GET http://localhost:8080/
     */
    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String home() {
        return "BrainStrom AI 服务已启动，/ask 接口可用";
    }

    /**
     * 流式问答接口 /ask
     * - 接收 POST JSON 请求：{"model":"gemini-2.0-flash-001","prompt":"..."}
     * - 响应 Content-Type: text/event-stream
     * - 每拿到一个片段，就推送一条 SSE 消息给客户端。
     */
    @PostMapping(
            value = "/ask",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter ask(@RequestBody AskRequest req) {

        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);

        new Thread(() -> {
            try {
                // 调用 Service，拿到 SDK 的 ResponseStream
                ResponseStream<GenerateContentResponse> stream =
                        service.generateStream(req.getModel(), req.getPrompt());

                // 遍历每个响应片段，按 SSE 规范推送给前端
                for (GenerateContentResponse part : stream) {
                    emitter.send(part.text());
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        }).start();

        return emitter;
    }

    /**
     * 请求体结构
     */
    public static class AskRequest {
        private String model;
        private String prompt;

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
    }
}
