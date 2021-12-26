package ru.otus.messagesystem.client;

import java.util.function.Consumer;

public interface MessageCallback<T> extends Consumer<T> {
    void accept(T data);
}
