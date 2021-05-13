package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.User;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(Long id);

    List<Account> findAll(Long idUser,Integer limit, Integer page);

    List<Account> findAllByName(String name, Integer limit, Integer page);

    Account createAccount(Account account);

    Account updateAccount(Account account);
}
