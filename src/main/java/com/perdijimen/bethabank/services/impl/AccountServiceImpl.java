package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.AccountDao;
import com.perdijimen.bethabank.dao.CategoryDao;
import com.perdijimen.bethabank.dao.TransactionDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Category;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.response.AnalyticResponse;
import com.perdijimen.bethabank.model.response.BalanceAnalyticResponse;
import com.perdijimen.bethabank.model.response.CategoryAnalytic;
import com.perdijimen.bethabank.model.response.CategoryAnalyticResponse;
import com.perdijimen.bethabank.repository.AccountRepository;
import com.perdijimen.bethabank.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private AccountRepository accountRepository;

    private AccountDao accountDao;
    private TransactionDao transactionDao;
    private CategoryDao categoryDao;

    public AccountServiceImpl(AccountRepository accountRepository, AccountDao accountDao,
                              TransactionDao transactionDao, CategoryDao categoryDao) {
        this.accountRepository = accountRepository;
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return this.accountDao.findById(id);
    }

    @Override
    public List<Account> findAll(Long idUser, Integer limit, Integer page) {
        return this.accountDao.findAll(idUser, limit, page);
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
                log.error("Cannot save the account: {} , error : {}", account, e);
            }
        }else{
            log.warn("Creating account with id");
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
