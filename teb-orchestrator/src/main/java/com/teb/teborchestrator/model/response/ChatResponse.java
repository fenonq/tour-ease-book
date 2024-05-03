package com.teb.teborchestrator.model.response;

import com.teb.teborchestrator.model.entity.Choice;
import com.teb.teborchestrator.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private List<Choice> choices;
}
