package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpCallbackServiceImpl<T extends ResultDataType> implements SimpCallbackService<T> {

    private final SimpMessagingTemplate template;

    @Override
    public void callback(T data) {
        template.convertAndSend("/", data);
    }

    @Override
    public void callbackForBatch(List<T> data) {
        template.convertAndSend("/users/page", data);
    }

}
