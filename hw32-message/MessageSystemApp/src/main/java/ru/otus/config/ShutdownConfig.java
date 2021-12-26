package ru.otus.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.MessageSystem;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ShutdownConfig {

    private final MessageSystem messageSystem;

    @Bean
    ServletListenerRegistrationBean<ServletContextListener> servletListener() {
        ServletListenerRegistrationBean<ServletContextListener> srb
                = new ServletListenerRegistrationBean<>();
        srb.setListener(new ServletContextListener() {
            @Override
            public void contextDestroyed(ServletContextEvent event) {
                try {
                    messageSystem.dispose();
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    log.error("Application closing");
                }
            }
        });
        return srb;
    }
}


