package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.model.response.AnalyticResponse;
import com.perdijimen.bethabank.model.response.BalanceAnalyticResponse;
import com.perdijimen.bethabank.model.response.CategoryAnalyticResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(Long id);

    List<Account> findAll(Long idUser,Integer limit, Integer page);

    List<AnalyticResponse> getAnalytics (Long idAccount, Boolean typePeriod);

    CategoryAnalyticResponse getAnalyticsCategory (Long idAccount);

    List<BalanceAnalyticResponse> getAnalyticsBalance (Long id, Boolean type, LocalDate start, LocalDate end);

    Account createAccount(Account account);

    Account updateAccount(Account account);
}
