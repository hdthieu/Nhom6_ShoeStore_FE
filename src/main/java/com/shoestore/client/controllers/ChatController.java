package com.shoestore.client.controllers;

import com.shoestore.client.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class ChatController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/chat")
    public ResponseEntity<Map> chatWithBot(@RequestBody Map<String, String> payload) {
        String fastApiUrl = "http://localhost:9091/chat";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payload, headers);

            // Gửi POST request đến FastAPI
            ResponseEntity<Map> response = restTemplate.exchange(
                    fastApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // Trả về response của FastAPI cho frontend
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu lỗi thì trả về lỗi 500 và message phù hợp
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("answer", "Server error: cannot get chatbot response"));
        }
    }
}
