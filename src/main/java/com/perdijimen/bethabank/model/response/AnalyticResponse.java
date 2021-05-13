package com.perdijimen.bethabank.model.response;

import java.time.LocalDate;

public class AnalyticResponse {

    private LocalDate date;
    private Double inCome;
    private Double expense;

    public AnalyticResponse() {
    }

    public AnalyticResponse(LocalDate date, Double inCome, Double expense) {
        this.date = date;
        this.inCome = inCome;
        this.expense = expense;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getInCome() {
        return inCome;
    }

    public void setInCome(Double inCome) {
        this.inCome = inCome;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }
}
