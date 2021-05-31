package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.model.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    List<User> findAll(Integer limit, Integer page);

    List<User> findAllByName(String name, Integer limit, Integer page);

    Optional<User> findByEmail(String email);

    UserResponse createUser(User user);

    User updateUser(User user);

    boolean deleteUserById(Long id);
}
