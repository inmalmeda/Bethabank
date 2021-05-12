package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.repository.AccountRepository;
import com.perdijimen.bethabank.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        log.info("createAccount");

        Account accountCreated = null;

        if(account.getId() == null){
            try{
                account.setCreated_at(LocalDate.now());
                account.setUpdated_at(LocalDate.now());
                accountCreated = accountRepository.save(account);
            }catch(Exception e) {
                log.error("Cannot save the user: {} , error : {}", account, e);
            }
        }else{
            log.warn("Creating user with id");
        }

        return accountCreated;
    }

    @Override
    public Account updateAccount(Account account) {
        log.info("updateAccount");

        Account result = null;

        if (accountRepository.existsById(account.getId())) {
            try{
                account.setUpdated_at(LocalDate.now());
                result = accountRepository.save(account);
            }catch(Exception e){
                log.error("Cannot save account: {} , error : {}", account, e);
            }
        }else{
            log.warn("Cannot save account: {}, because it doesn´t exist", account);
        }
        return result;
    }
}
