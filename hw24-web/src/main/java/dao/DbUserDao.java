package dao;

import core.repository.DataTemplate;
import core.repository.DataTemplateHibernate;
import core.repository.HibernateUtils;
import core.sessionmanager.TransactionManager;
import core.sessionmanager.TransactionManagerHibernate;
import crm.model.Client;
import crm.service.DBServiceClient;
import crm.service.DbServiceClientImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class DbUserDao implements UserDao {

    private final DBServiceClient dbServiceClient;

    public DbUserDao(Configuration configuration) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class);
        TransactionManager transactionManager = new TransactionManagerHibernate(sessionFactory);
        DataTemplate<Client> clientTemplate = new DataTemplateHibernate<>(Client.class);
        dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
    }

    @Override
    public Optional<Client> findById(long id) {
        return dbServiceClient.get(id);
    }

    @Override
    public Optional<Client> findRandomUser() {
        return Optional.ofNullable(dbServiceClient.findRandom());
    }

    @Override
    public Optional<Client> findByLogin(String login) {
        return dbServiceClient.findByField("login", login);
    }

    @Override
    public List<Client> findAll() {
        return dbServiceClient.findAll();
    }

    @Override
    public Client save(Client client) {
        return dbServiceClient.save(client);
    }
}
