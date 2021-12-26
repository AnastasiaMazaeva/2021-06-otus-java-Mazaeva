package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.service.FrontendService;
import ru.otus.web.dto.ClientDto;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final FrontendService frontendService;

    @MessageMapping("/all")
    @SendTo("/users/page")
    public void getUsers() throws InterruptedException {
        Thread.sleep(1000);
        frontendService.getAll();
        Thread.sleep(1000);
    }

    @MessageMapping("/create")
    @SendTo("/users/page")
    public void createUser(ClientDto client) {
        frontendService.saveUserData(client);
    }

    @MessageMapping("/edit")
    @SendTo("/users/page")
    public void updateUser(ClientDto client) {
        frontendService.saveUserData(client);
    }
}
