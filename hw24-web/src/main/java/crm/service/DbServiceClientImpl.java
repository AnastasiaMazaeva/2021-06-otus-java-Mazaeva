package crm.service;


import core.repository.DataTemplate;
import core.sessionmanager.TransactionManager;
import crm.model.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class DbServiceClientImpl implements DBServiceClient {

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public Client save(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> get(long id) {
        return transactionManager.doInTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }

    @Override
    public Client findRandom() {
        return transactionManager.doInTransaction(session -> {
            var clientOptional = clientDataTemplate.findRandom(session);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public Optional<Client> findByField(String fieldName, String fieldValue) {
        return transactionManager.doInTransaction(session -> {
            var clientOptional = clientDataTemplate.findByField(session, fieldName, fieldValue);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }
}
