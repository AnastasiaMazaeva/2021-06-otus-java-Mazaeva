package ru.otus.crm.service;


import ru.otus.crm.model.Client;
import ru.otus.crm.model.Role;

import java.util.List;

public interface DBServiceClient {

    void save(Client client);

    List<Client> findAll();

    void update(Long id, String login, Role role);
}
