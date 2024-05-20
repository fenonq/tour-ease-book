package com.teb.aiassistantservice.service.impl;

import com.teb.aiassistantservice.model.ChatRequest;
import com.teb.aiassistantservice.model.ChatResponse;
import com.teb.aiassistantservice.model.Message;
import com.teb.aiassistantservice.service.AssistantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private final RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Override
    public ChatResponse chat(List<Message> prompt) {
        log.info("Sending request to ai assistant..");
        List<Message> defaultPrompt = new ArrayList<>(List.of(Message.builder()
                .role("system")
                .content("Ти онлайн асистент, який використовується як чат підтримки на сайті для букання готелей. " +
                        "Надавай користувачам дуже короткі та точні відповіді на їхні питання, пов'язані з подорожуванням " +
                        "і якоюсь загальною інформацію про країни та міста, " +
                        "такі як погода, визначні місця і т.д. На питання, які не стосуються теми подорожей, відповідай," +
                        " що не володієш цією інформацією і проси адресувати ці питання відповідним ресурсам. " +
                        "НЕ додавай в кінці кожного повідомлення 'Якщо у вас є ще питання про подорожі, не соромтесь запитувати!'")
                .build()));
        defaultPrompt.addAll(prompt);
        ChatRequest request = new ChatRequest(model, defaultPrompt);
        return restTemplate.postForObject(apiUrl, request, ChatResponse.class);
    }
}
