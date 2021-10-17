package crm.service;


import crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    void save(Client client);

    List<Client> findAll();

    Optional<Client> findByLogin(String fieldValue);
}
