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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private final RestTemplate restTemplate;

    @Value("${hotels.info.path}")
    private String pathToFile;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Override
    public ChatResponse chat(List<Message> prompt) {
        log.info("Sending request to ai assistant..");
        String additionalInfo = getInfoFromFile();
        List<Message> defaultPrompt = new ArrayList<>(List.of(Message.builder()
                .role("system")
                .content("Ви онлайн асистент, який використовується як чат підтримки на сайті для букання готелей. " +
                        "Надавайте користувачам дуже короткі та точні відповіді на їхні питання, пов'язані з подорожуванням " +
                        "і якоюсь загальною інформацію про країни та міста, " +
                        "такі як погода, визначні місця і т.д. На питання, які не стосуються теми подорожей, відповідайте," +
                        " що не володієте цією інформацією і просіть адресувати ці питання відповідним ресурсам. " +
                        "НЕ додавайте в кінці кожного повідомлення 'Якщо у вас є ще питання про подорожі, не соромтесь запитувати!'" +
                        "Інформація про готелі, якою ви володієте: " + additionalInfo)
                .build()));
        defaultPrompt.addAll(prompt);
        ChatRequest request = new ChatRequest(model, defaultPrompt);
        return restTemplate.postForObject(apiUrl, request, ChatResponse.class);
    }

    private String getInfoFromFile() {
        try {
            return Files.readAllLines(Paths.get(pathToFile)).stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.error("Failed to load hotel information from file", e);
            return "";
        }
    }

}
