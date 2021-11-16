package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.web.dto.ClientDto;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final DBServiceClient clientService;

    @GetMapping("/users")
    public ModelAndView getUsers() {
        return getModel();
    }

    @PostMapping("/users")
    public ModelAndView createUser(@ModelAttribute("clientDto") ClientDto client) {
        clientService.save(new Client(client.getLogin(), client.getRole()));
        return getModel();
    }

    @PostMapping("/users/edit")
    public ModelAndView updateUser(@ModelAttribute("updateClientDto") ClientDto client) {
        clientService.update(client.getId(), client.getLogin(), client.getRole());
        return getModel();
    }

    private ModelAndView getModel() {
        ModelAndView model = new ModelAndView("users");
        model.addObject("clientDto", new ClientDto());
        model.addObject("updateClientDto", new ClientDto());
        model.addObject("users", clientService.findAll());
        return model;
    }
}
