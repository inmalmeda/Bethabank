package com.perdijimen.bethabank.model.response;

public class LoanResponse {

    private Double amount;

    private Double amountPerFee;

    private Integer fee;

    private Boolean created;

    private Double interestRate;

    public LoanResponse() {
    }

    public LoanResponse(Integer fee, Double interestRate) {
        this.fee = fee;
        this.interestRate = interestRate;
    }

    public LoanResponse(Double amount, Double amountPerFee, Integer fee, Boolean created, Double interestRate) {
        this.amount = amount;
        this.amountPerFee = amountPerFee;
        this.fee = fee;
        this.created = created;
        this.interestRate = interestRate;
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

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Boolean getCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
