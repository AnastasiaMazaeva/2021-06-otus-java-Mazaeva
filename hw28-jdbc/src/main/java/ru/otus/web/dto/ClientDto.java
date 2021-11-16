package ru.otus.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.crm.model.Role;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String login;
    private Role role;
}
