package ru.otus.handler;

import lombok.extern.slf4j.Slf4j;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;

import java.util.Optional;

@Slf4j
public class ResponseHandler implements RequestHandler {

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        log.info("new message:{}", msg);
        try {
            var callback = msg.getCallback();
            if (callback != null) {
                callback.accept(msg.getData());
            } else {
                log.error("callback for Id:{} not found", msg.getId());
            }
        } catch (Exception ex) {
            log.error("msg:{}", msg, ex);
        }
        return Optional.empty();
    }
}
