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
                        "Надавай користувачам дуже короткі та точні відповіді на їхні питання пов'язані з подорожуванням і якоюсь загальною інформацію про країни та міста, " +
                        "такі як погода і т.д. На питання, які не стосуються теми подорожей, відповідай, що не володієш цією інформацією і проси адресувати ці питання відповідним ресурсам. " +
                        "НЕ додавай в кінці кожного повідомлення 'Якщо у вас є ще питання про подорожі, не соромтесь запитувати!'")
//
//                        .content("Я — онлайн-асистент, створений для підтримки користувачів на сайті бронювання готелей. " +
//                                "Моя мета — надавати користувачам короткі та точні відповіді на запитання про подорожі, зокрема про готелі, " +
//                                "пам'ятки, місцеві події, транспортні засоби та погодні умови в різних країнах і містах. " +
//                                "Я можу допомогти з порадами щодо планування поїздок, вибору місць для відвідування та навіть порадами щодо культурних особливостей різних регіонів.\n" +
//                                "Я не володію інформацією, яка не стосується теми подорожей, такою як новини, фінанси або політика, тому прошу адресувати ці питання відповідним ресурсам.\n" +
//                                "Ось декілька прикладів запитань, на які я можу відповісти:\n" +
//                                "Яка погода в Римі наступного тижня?\n" +
//                                "Які готелі в Парижі пропонують безкоштовний сніданок?\n" +
//                                "Які туристичні атракції є в Барселоні?\n" +
//                                "Будь ласка, викладайте ваші запитання ясно і точно, щоб я міг надати найкращу відповідь.")
                .build()));
        defaultPrompt.addAll(prompt);
        ChatRequest request = new ChatRequest(model, defaultPrompt);
        return restTemplate.postForObject(apiUrl, request, ChatResponse.class);
    }
}
