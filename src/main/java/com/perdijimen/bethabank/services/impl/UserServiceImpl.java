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
import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {


    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        log.info("createUSer");

        User userCreated = null;

        if(user.getId() == null){
            try{
                user.setCreated_at(LocalDate.now());
                user.setUpdated_at(LocalDate.now());
                userCreated = userRepository.save(user);
            }catch(Exception e) {
                log.error("Cannot save the user: {} , error : {}", user, e);
            }
        }else{
            log.warn("Creating user with id");
        }

        return userCreated;
    }

    @Override
    public User updateUser(User user) {
        log.info("updateUser");

        User result = null;

        if (userRepository.existsById(user.getId())) {
            try{
                user.setUpdated_at(LocalDate.now());
                result = userRepository.save(user);
            }catch(Exception e){
                log.error("Cannot save user: {} , error : {}", user, e);
            }
        }else{
            log.warn("Cannot save user: {}, because it doesn´t exist", user);
        }
        return result;
    }

}
