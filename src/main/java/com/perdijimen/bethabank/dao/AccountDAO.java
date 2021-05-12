package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {

    Optional<Account> findByIdFromEntityManager(Long id);
    List<Account> findAll(Integer limite, Integer pagina);
}
