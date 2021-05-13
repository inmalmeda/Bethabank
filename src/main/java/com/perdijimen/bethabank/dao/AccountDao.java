package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.response.AnalyticResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountDao {

    List<Account> findAll(Long idUser,Integer limite, Integer pagina);

    Optional<Account> findById(Long id);

}
