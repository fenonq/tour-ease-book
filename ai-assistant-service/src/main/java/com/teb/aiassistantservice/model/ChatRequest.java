package com.teb.aiassistantservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    public ChatRequest(String model, List<Message> prompt) {
        this.model = model;
        this.n = 1;

        this.messages = new ArrayList<>(prompt);
    }
}

