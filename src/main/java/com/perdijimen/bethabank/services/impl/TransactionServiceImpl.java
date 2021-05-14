package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.TransactionDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.repository.AccountRepository;
import com.perdijimen.bethabank.repository.TransactionRepository;
import com.perdijimen.bethabank.services.AccountService;
import com.perdijimen.bethabank.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private TransactionRepository transactionRepository;
    private AccountService accountService;
    private AccountRepository accountRepository;
    private TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService, AccountRepository accountRepository, TransactionDao transactionDao) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.transactionDao = transactionDao;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return this.transactionDao.findById(id);
    }

    @Override
    public List<Transaction> findAll(Long idAccount, Boolean type, Integer limit, Integer page) {
        log.debug("Find all transactions");
        return transactionDao.findAll(idAccount, type, limit, page);
    }

    @Override
    public Transaction createTransaction(Transaction transaction, Long idAccount) {
        log.info("createTransaction");

        Transaction transactionCreated = null;

        if(transaction.getId() == null && accountRepository.existsById(idAccount)){
            try{
                transaction.setTransaction_date(LocalDate.now());
                transaction.setTransaction_time(LocalTime.now());
                transactionCreated = transactionRepository.save(transaction);

                if(transactionCreated != null){
                    manageBalanceAccount(idAccount, transaction.getAmount(), transaction.getIncome());
                }

            }catch(Exception e) {
                log.error("Cannot save the transaction: {} , error : {}", transaction, e);
            }
        }else{
            log.warn("Creating transaction with id");
        }

        return transactionCreated;
    }

    private void manageBalanceAccount(Long idAccount, Double amount, boolean isIncome){

        Optional<Account> accountToUpdate = accountRepository.findById(idAccount);

        if (accountToUpdate.isPresent()) {
            Double total = accountToUpdate.get().getTotal_amount();
            total = isIncome ? total + amount : total - amount;
            accountToUpdate.get().setTotal_amount(total);

            accountService.updateAccount(accountToUpdate.get());
        }
    }

}
