package ru.otus.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Role;
import ru.otus.messagesystem.client.ResultDataType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto implements ResultDataType {

    private Long id;
    private String login;
    private Role role;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.login = client.getLogin();
        this.role = client.getRole();
    }

    public Client toClient() {
        return new Client(this.id, this.login, this.role);
    }
}
