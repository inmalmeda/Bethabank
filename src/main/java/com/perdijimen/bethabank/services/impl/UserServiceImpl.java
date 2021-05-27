package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.UserDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.repository.UserRepository;
import com.perdijimen.bethabank.services.AccountService;
import com.perdijimen.bethabank.services.CardService;
import com.perdijimen.bethabank.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private UserRepository userRepository;
    private UserDao userDao;

    @Autowired
    private CardService cardService;
    @Autowired
    private AccountService accountService;

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
    public Optional<User> findByEmail(String email) {

        Optional<User> user = this.userRepository.findByEmail(email);

        return this.userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        log.info("createUSer");

        User userCreated = null;

        Optional<User> userEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> userDNI = userRepository.findByDNI(user.getDNI());

        if(user.getId() == null && !userEmail.isPresent() && !userDNI.isPresent()) {

                String md5Hex = DigestUtils.md5Hex(user.getPassword()).toUpperCase();
                user.setPassword(md5Hex);

                try {
                    user.setCreated_at(LocalDate.now());
                    user.setUpdated_at(LocalDate.now());
                    userCreated = userRepository.save(user);
                } catch (Exception e) {
                    log.error("Cannot save the user: {} , error : {}", user, e);
                }
        } else {
            log.warn("Creating user with id");
        }
        return userCreated;
    }

    @Override
    public User updateUser(User userToUpdate) {
        log.info("updateUser");
        User result = null;

        Optional<User>user = findById(userToUpdate.getId());

        if(user.isPresent()){
                try{
                    userToUpdate.setCardList(user.get().getCardList());
                    userToUpdate.setTitularAccountList(user.get().getTitularAccountList());
                    userToUpdate.setOwnerAccountList(user.get().getOwnerAccountList());
                    userToUpdate.setUpdated_at(LocalDate.now());
                    result = userRepository.save(userToUpdate);
                }catch(Exception e){
                    log.error("Cannot save user: {} , error : {}", userToUpdate, e);
                }
        }else{
            log.warn("Cannot save user: {}, because it doesn´t exist", userToUpdate);
        }


        return result;
    }

    @Override
    public boolean deleteUserById(Long id) {
        log.debug("Delete an user by id: {}", id);

        User userToDelete = manager.find(User.class, id);

        if (userToDelete != null) {
            try{

                for (Account account : userToDelete.getOwnerAccountList()) {
                    List <User> userList = new ArrayList<>();
                    for (User user: account.getUserList()) {
                        if (user.getId() != id){
                            userList.add(user);
                        }
                    }
                    account.setUserList(userList);
                    accountService.updateAccountObject(account);
                }

                for(Account account : userToDelete.getTitularAccountList()){
                    accountService.deleteAccountById(account.getId());
                }

                userRepository.deleteById(id);

            }catch(Exception e){
                log.error("Cannot delete user with id {}", id);
                return false;
            }
        }else {
            log.error("Doesn´t exist user with id {}", id);
            return false;
        }
        return true;
    }

}
