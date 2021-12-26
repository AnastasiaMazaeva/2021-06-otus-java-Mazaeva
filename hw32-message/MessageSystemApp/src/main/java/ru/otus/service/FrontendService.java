package ru.otus.service;

import ru.otus.web.dto.ClientDto;

public interface FrontendService {
    void saveUserData(ClientDto clientDto);

    void getAll();
}
