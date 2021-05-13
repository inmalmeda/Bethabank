package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(Long id);

    List<User> findAll(Integer limite, Integer pagina);

    List<User> findAllByName(String name, Integer limit, Integer page);
}
