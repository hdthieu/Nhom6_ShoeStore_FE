package com.shoestore.client.service;

import org.springframework.stereotype.Service;

@Service
public interface ChatBotService {
    public String askBot(String userQuery);
}
