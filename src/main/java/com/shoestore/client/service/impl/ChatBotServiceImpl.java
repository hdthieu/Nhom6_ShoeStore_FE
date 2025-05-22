package com.shoestore.client.service.impl;

import com.shoestore.client.dto.response.SearchResponse;
import com.shoestore.client.service.ChatBotService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatBotServiceImpl implements ChatBotService {
    private final WebClient webClient = WebClient.create("http://localhost:9091");

    @Override
    public String askBot(String userQuery) {
        Map<String, Object> body = new HashMap<>();
        body.put("query", userQuery); // bỏ top_k

        // Gọi endpoint mới
        Map<String, String> response = webClient.post()
                .uri("/chat")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class) // vì /chat trả về {"answer": "..."}
                .block();

        return response != null ? response.get("answer") : "❌ Không có phản hồi từ chatbot.";
    }

}
