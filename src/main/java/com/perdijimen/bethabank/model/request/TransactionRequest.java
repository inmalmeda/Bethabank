package com.perdijimen.bethabank.model.request;

import com.perdijimen.bethabank.model.Transaction;

public class TransactionRequest {

    private Long idAccount;

    private Long idCard;

    private Double amount;

    private String accountOut;

    private Boolean income;

    private Long idCategory;


    public TransactionRequest() {
    }

    public TransactionRequest(Long idAccount, Long idCard, Double amount, String accountOut, Boolean income, Long idCategory) {
        this.idAccount = idAccount;
        this.idCard = idCard;
        this.amount = amount;
        this.accountOut = accountOut;
        this.income = income;
        this.idCategory = idCategory;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAccountOut() {
        return accountOut;
    }

    public void setAccountOut(String accountOut) {
        this.accountOut = accountOut;
    }

    public Boolean getIncome() {
        return income;
    }

    public void setIncome(Boolean income) {
        this.income = income;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }
}
