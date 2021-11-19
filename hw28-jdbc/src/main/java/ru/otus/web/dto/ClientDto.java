package ru.otus.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Role;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String login;
    private Role role;

    public Client toClient() {
        return new Client(this.id, this.login, this.role);
    }
}
