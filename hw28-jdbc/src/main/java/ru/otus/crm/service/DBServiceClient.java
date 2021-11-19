package ru.otus.crm.service;


import ru.otus.crm.model.Client;

import java.util.List;

public interface DBServiceClient {

    void save(Client client);

    List<Client> findAll();

}
