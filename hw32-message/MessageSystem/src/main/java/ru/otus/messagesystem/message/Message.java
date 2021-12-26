package ru.otus.messagesystem.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.otus.messagesystem.client.MessageCallback;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Message<T> {

    private final UUID id;
    private final String from;
    private final String to;
    private final UUID sourceMessageId;
    private final MessageType type;
    private final List<T> data;
    private final MessageCallback<List<T>> callback;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message<?> message = (Message<?>) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", sourceMessageId=" + sourceMessageId +
                ", type='" + type + '\'' +
                '}';
    }
}
