package com.investment.neosurge.FinSightLLMService.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OpenAIRequest {
    private String model;
    private List<Message> messages;
    private int max_tokens;

    public OpenAIRequest(String model, List<Message> messages, int max_tokens) {
        this.model = model;
        this.messages = messages;
        this.max_tokens = max_tokens;
    }
}

