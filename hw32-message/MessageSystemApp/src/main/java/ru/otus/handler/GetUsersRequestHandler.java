package ru.otus.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.web.dto.ClientDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class GetUsersRequestHandler implements RequestHandler {

    private final DBServiceClient dbServiceClient;

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        List<ClientDto> clients = dbServiceClient.findAll().stream().map(ClientDto::new).collect(Collectors.toList());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (List<T>) clients));
    }
}