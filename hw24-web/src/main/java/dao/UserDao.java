package dao;


import crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<Client> findById(long id);
    Optional<Client> findRandomUser();
    Optional<Client> findByLogin(String login);

    List<Client> findAll();

    Client save(Client client);
}