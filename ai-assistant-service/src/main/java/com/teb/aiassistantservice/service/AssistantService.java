package com.teb.aiassistantservice.service;

import com.teb.aiassistantservice.model.ChatResponse;
import com.teb.aiassistantservice.model.Message;

import java.util.List;

public interface AssistantService {

    ChatResponse chat(List<Message> prompt);

}
