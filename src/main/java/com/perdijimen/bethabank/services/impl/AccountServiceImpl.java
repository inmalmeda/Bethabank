package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.repository.AccountRepository;
import com.perdijimen.bethabank.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        if (ObjectUtils.isEmpty(account))
            return null;

        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        log.info("updateAccount");
        if (ObjectUtils.isEmpty(account))
            return null;
        return accountRepository.save(account);
    }
}
