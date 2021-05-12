package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.repository.CardRepository;
import com.perdijimen.bethabank.repository.TransactionRepository;
import com.perdijimen.bethabank.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        log.info("createTransaction");

        Transaction transactionCreated = null;

        if(transaction.getId() == null){
            try{
                transaction.setTransaction_date(LocalDate.now());
                transactionCreated = transactionRepository.save(transaction);
            }catch(Exception e) {
                log.error("Cannot save the transaction: {} , error : {}", transaction, e);
            }
        }else{
            log.warn("Creating transaction with id");
        }

        return transactionCreated;
    }
}
