package com.teb.teborchestrator.feign;

import com.teb.teborchestrator.model.entity.Message;
import com.teb.teborchestrator.model.response.ChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("AI-ASSISTANT-SERVICE")
public interface AiAssistantClient {

    @PostMapping("/chat")
    ChatResponse chat(@RequestBody List<Message> prompt);

}
