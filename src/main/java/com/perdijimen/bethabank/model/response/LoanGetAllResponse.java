package com.perdijimen.bethabank.model.response;

public class LoanGetAllResponse {

    private Double amount;

    private Double amountPerFee;

    private Double amountLoan;

    private Integer fee;

    private Integer feeLoan;

    private Double interestRate;

    private String accountIncome;

    private String accountCollection;

    public LoanGetAllResponse() {
    }

    public LoanGetAllResponse(Double amount, Double amountPerFee, Double amountLoan, Integer fee, Integer feeLoan, Double interestRate,
                              String accountIncome, String accountCollection) {
        this.amount = amount;
        this.amountPerFee = amountPerFee;
        this.amountLoan = amountLoan;
        this.fee = fee;
        this.feeLoan = feeLoan;
        this.interestRate = interestRate;
        this.accountIncome = accountIncome;
        this.accountCollection = accountCollection;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountPerFee() {
        return amountPerFee;
    }

    public void setAmountPerFee(Double amountPerFee) {
        this.amountPerFee = amountPerFee;
    }

    public Double getAmountLoan() {
        return amountLoan;
    }

    public void setAmountLoan(Double amountLoan) {
        this.amountLoan = amountLoan;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getAccountIncome() {
        return accountIncome;
    }

    public void setAccountIncome(String accountIncome) {
        this.accountIncome = accountIncome;
    }

    public String getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(String accountCollection) {
        this.accountCollection = accountCollection;
    }

    public Integer getFeeLoan() {
        return feeLoan;
    }

    public void setFeeLoan(Integer feeLoan) {
        this.feeLoan = feeLoan;
    }
}
