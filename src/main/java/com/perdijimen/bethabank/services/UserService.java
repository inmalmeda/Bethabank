package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    List<User> findAll(Integer limit, Integer page);

    List<User> findAllByName(String name, Integer limit, Integer page);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUserById(Long id);
}
