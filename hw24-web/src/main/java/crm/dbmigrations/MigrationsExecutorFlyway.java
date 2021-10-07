package crm.dbmigrations;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.hibernate.cfg.Configuration;

@Slf4j
public class MigrationsExecutorFlyway {

    private final Flyway flyway;

    public MigrationsExecutorFlyway(Configuration configuration) {
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        flyway = Flyway.configure()
                .dataSource(dbUrl, dbUserName, dbPassword)
                .locations("classpath:/db/migration")
                .load();
    }

    public void executeMigrations() {
        log.info("db migration started...");
        flyway.migrate();
        log.info("db migration finished.");
    }
}
