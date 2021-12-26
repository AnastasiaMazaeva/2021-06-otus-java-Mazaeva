package ru.otus.messagesystem.message;

public enum MessageType {
    GET_USERS("getUsers"),
    SAVE_USER("saveUser"), VOID_MESSAGE("void");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
