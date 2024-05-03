package com.teb.aiassistantservice.service.impl;

import com.teb.aiassistantservice.model.ChatRequest;
import com.teb.aiassistantservice.model.ChatResponse;
import com.teb.aiassistantservice.model.Message;
import com.teb.aiassistantservice.service.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private final RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    public ChatResponse chat(List<Message> prompt) {
        ChatRequest request = new ChatRequest(model, prompt);
        return restTemplate.postForObject(apiUrl, request, ChatResponse.class);
    }
}
