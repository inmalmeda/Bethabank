package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.repository.UserRepository;
import com.perdijimen.bethabank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {


    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        log.info("createUSer");
        if (ObjectUtils.isEmpty(user))
            return null;

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        log.info("updateUser");
        if (ObjectUtils.isEmpty(user))
            return null;
        return userRepository.save(user);
    }
}
