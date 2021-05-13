package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAll(Long idAccount, Boolean type, Integer limit, Integer page);

    Transaction createTransaction(Transaction transaction, Long idAccount);
}
