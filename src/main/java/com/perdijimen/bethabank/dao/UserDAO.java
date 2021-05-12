package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> findByIdFromEntityManager(Long id);
    List<User> findAll(Integer limite, Integer pagina);
}
