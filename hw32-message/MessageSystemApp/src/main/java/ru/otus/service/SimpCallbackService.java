package ru.otus.service;

import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

public interface SimpCallbackService<T extends ResultDataType> {
    void callback(T data);

    void callbackForBatch(List<T> data);
}
