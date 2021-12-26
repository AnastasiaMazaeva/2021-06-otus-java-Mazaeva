package ru.otus.messagesystem.message;

import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MessageBuilder {
    private static final Message<ResultDataTypeVoid> VOID_MESSAGE =
            new Message<>(UUID.randomUUID(), null, null,
                    null, MessageType.VOID_MESSAGE, Collections.singletonList(new ResultDataTypeVoid()), null);

    private MessageBuilder() {
    }

    public static Message<ResultDataTypeVoid> getVoidMessage() {
        return VOID_MESSAGE;
    }

    public static <T extends ResultDataType> Message<T> buildReplyMessage(Message<T> message, List<T> data) {
        return buildMessage(message.getTo(), message.getFrom(), message.getId(), data, message.getType(), message.getCallback());
    }

    public static <T extends ResultDataType> Message<T> buildMessage(String from, String to, UUID sourceMessageId,
                                                                     List<T> data, MessageType msgType, MessageCallback<List<T>> callback) {
        UUID id = UUID.randomUUID();
        return new Message<>(id, from, to, sourceMessageId, msgType, data, callback);
    }

    public static class ResultDataTypeVoid implements ResultDataType {

    }
}
