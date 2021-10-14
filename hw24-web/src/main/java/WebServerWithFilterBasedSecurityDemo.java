import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.repository.DataTemplate;
import core.repository.DataTemplateHibernate;
import core.repository.HibernateUtils;
import core.sessionmanager.TransactionManager;
import core.sessionmanager.TransactionManagerHibernate;
import crm.dbmigrations.MigrationsExecutorFlyway;
import crm.model.Client;
import crm.service.DBServiceClient;
import crm.service.DbServiceClientImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import server.UsersWebServer;
import server.UsersWebServerWithFilterBasedSecurity;
import service.TemplateProcessor;
import service.TemplateProcessorImpl;
import service.UserAuthService;
import service.UserAuthServiceImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8081

    // Страница пользователей
    http://localhost:8081/users

*/
public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8081;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        MigrationsExecutorFlyway flyway = new MigrationsExecutorFlyway(configuration);

        flyway.executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class);
        TransactionManager transactionManager = new TransactionManagerHibernate(sessionFactory);
        DataTemplate<Client> clientTemplate = new DataTemplateHibernate<>(Client.class);
        DBServiceClient dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceClient);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceClient, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
