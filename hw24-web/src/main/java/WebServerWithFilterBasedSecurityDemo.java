import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import crm.dbmigrations.MigrationsExecutorFlyway;
import crm.model.Client;
import crm.model.Role;
import dao.DbUserDao;
import dao.UserDao;
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
    http://localhost:8081/users/create

    // REST сервис
    http://localhost:8081/api/user/3
*/
public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8081;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        MigrationsExecutorFlyway flyway = new MigrationsExecutorFlyway(configuration);
        flyway.executeMigrations();
        UserDao userDao = new DbUserDao(configuration);
        userDao.save(new Client("admin", Role.ADMIN, "admin"));
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, userDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
