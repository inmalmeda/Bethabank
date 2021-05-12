package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Account;

public interface AccountService {

    Account createAccount(Account account);

    Account updateAccount(Account account);
}
