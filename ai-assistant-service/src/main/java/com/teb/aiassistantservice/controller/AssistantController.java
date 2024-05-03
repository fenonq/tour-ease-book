package com.teb.aiassistantservice.controller;

import com.teb.aiassistantservice.model.ChatResponse;
import com.teb.aiassistantservice.model.Message;
import com.teb.aiassistantservice.service.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody List<Message> prompt) {
        return assistantService.chat(prompt);
    }
}
