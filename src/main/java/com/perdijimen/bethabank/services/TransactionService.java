package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.User;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Optional<Transaction> findById(Long id);

    List<Transaction> findAll(Long idAccount, Boolean type, Integer limit, Integer page);

    Transaction createTransaction(Transaction transaction, Long idAccount);
}
