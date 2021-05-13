package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Transaction;

import java.util.List;

public interface TransactionDao {
    List<Transaction> findAll(Long idAccount,Boolean type, Integer limite, Integer pagina);
}
