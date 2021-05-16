package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.request.AccountRequest;
import com.perdijimen.bethabank.model.request.AccountUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(Long id);

    List<Account> findAll(Long idUser,Integer limit, Integer page);

    Account createAccount(AccountRequest account);

    Account updateAccount(AccountUpdateRequest account);

    Account updateAccountObject(Account account);
}
