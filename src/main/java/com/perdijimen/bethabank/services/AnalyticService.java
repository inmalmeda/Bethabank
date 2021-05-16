package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.response.AnalyticResponse;
import com.perdijimen.bethabank.model.response.BalanceAnalyticResponse;
import com.perdijimen.bethabank.model.response.CategoryAnalyticResponse;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticService {

    List<AnalyticResponse> getAnalytics (Long idAccount, Boolean typePeriod);

    CategoryAnalyticResponse getAnalyticsCategory (Long idAccount);

    List<BalanceAnalyticResponse> getAnalyticsBalance (Long id, Long idUser, Boolean type, LocalDate start, LocalDate end);
}
