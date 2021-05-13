package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.UserDao;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.repository.UserRepository;
import com.perdijimen.bethabank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    private UserRepository userRepository;
    private UserDao userDao;

    public UserServiceImpl(UserRepository userRepository, UserDao userDao) {
        this.userRepository = userRepository;
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userDao.findById(id);
    }

    @Override
    public List<User> findAll(Integer limit, Integer page) {
        return this.userDao.findAll(limit, page);
    }

    @Override
    public List<User> findAllByName(String name, Integer limit, Integer page) {
        return this.userDao.findAllByName(name, limit, page);
    }

    @Override
    public User createUser(User user) {

        log.info("createUSer");

        User userCreated = null;

        if(user.getId() == null){

           //Comprobar DNI y comprobar Email

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
