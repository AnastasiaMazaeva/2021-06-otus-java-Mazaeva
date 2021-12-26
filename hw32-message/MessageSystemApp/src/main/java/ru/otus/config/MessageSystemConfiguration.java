package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import ru.otus.constants.Constants;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.handler.GetUsersRequestHandler;
import ru.otus.handler.ResponseHandler;
import ru.otus.handler.SaveUserRequestHandler;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@Configuration
@RequiredArgsConstructor
public class MessageSystemConfiguration {

    private final DBServiceClient dbServiceClient;

    @Bean("messageSystem")
    @ConditionalOnMissingBean(MessageSystem.class)
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    @DependsOn("messageSystem")
    @Qualifier(Constants.DATABASE_CLIENT_NAME)
    public MsClient databaseClient(final MessageSystem messageSystem) {
        var requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_USER, new SaveUserRequestHandler(dbServiceClient));
        requestHandlerDatabaseStore.addHandler(MessageType.GET_USERS, new GetUsersRequestHandler(dbServiceClient));
        var databaseMsClient = new MsClientImpl(Constants.DATABASE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean
    @DependsOn("messageSystem")
    @Qualifier(Constants.RESPONSE_CLIENT_NAME)
    public MsClient responseClient(final MessageSystem messageSystem) {
        var responseHandlerDatabaseStore = new HandlersStoreImpl();
        responseHandlerDatabaseStore.addHandler(MessageType.SAVE_USER, new ResponseHandler());
        responseHandlerDatabaseStore.addHandler(MessageType.GET_USERS, new ResponseHandler());
        var databaseMsClient = new MsClientImpl(Constants.RESPONSE_CLIENT_NAME, messageSystem, responseHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

}
