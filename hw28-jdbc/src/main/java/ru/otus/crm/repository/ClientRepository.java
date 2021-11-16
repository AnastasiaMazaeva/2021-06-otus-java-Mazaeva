package ru.otus.crm.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Role;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {


    @Modifying
    @Query("update client set login = :newName, role = :newRole where id = :id")
    void updateNameAndRole(@Param("id") long id, @Param("newName") String newName, @Param("newRole") Role role);

    List<Client> findAll(Sort sort);
}
