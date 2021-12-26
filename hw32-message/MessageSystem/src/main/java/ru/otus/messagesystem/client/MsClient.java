package ru.otus.messagesystem.client;

import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

import java.util.List;

public interface MsClient {

    <T extends ResultDataType> boolean sendMessage(Message<T> msg);

    <T extends ResultDataType> void handle(Message<T> msg);

    String getName();

    <T extends ResultDataType> Message<T> produceMessage(String to, List<T> data, MessageType msgType, MessageCallback<List<T>> callback);
}
