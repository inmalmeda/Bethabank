package com.perdijimen.bethabank.model.response;

import java.time.LocalDate;
import java.time.LocalTime;

public class BalanceAnalyticResponse {

    private Long id;
    private LocalDate date;
    private Double balance;

    public BalanceAnalyticResponse() {
    }

    public BalanceAnalyticResponse(Long id, LocalDate date, Double balance) {
        this.id = id;
        this.date = date;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
