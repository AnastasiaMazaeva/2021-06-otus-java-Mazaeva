package ru.otus.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.web.dto.ClientDto;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final DBServiceClient clientService;

    @GetMapping("/users")
    public ModelAndView getUsers() {
        return getModel("users");
    }

    @PostMapping("/users")
    public ModelAndView createUser(@ModelAttribute("clientDto") ClientDto client) {
        clientService.save(client.toClient());
        return getModel("users");
    }

    @PostMapping("/users/edit")
    public ModelAndView updateUser(@ModelAttribute("updateClientDto") ClientDto client) {
        clientService.save(client.toClient());
        return getModel("redirect:/users");
    }

    private ModelAndView getModel(String viewName) {
        ModelAndView model = new ModelAndView(viewName);
        model.addObject("clientDto", new ClientDto());
        model.addObject("updateClientDto", new ClientDto());
        model.addObject("users", clientService.findAll());
        return model;
    }
}
