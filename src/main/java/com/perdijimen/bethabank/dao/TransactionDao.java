package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    Optional<Transaction> findById(Long id);

    List<Transaction> findAll(Long idAccount,Boolean type, Integer limite, Integer pagina);

    List<Transaction> getAnalyticTransactions (Long idAccount, LocalDate start, LocalDate end);
}
