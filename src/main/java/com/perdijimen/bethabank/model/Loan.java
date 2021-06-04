package com.perdijimen.bethabank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "loans")
public class Loan {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave primaria tipo Long")
    private Long id;

    @ApiModelProperty("Cantidad del préstamo que falta por pagar")
    private Double amount;

    @ApiModelProperty("Cantidad a pagar por cada cuota")
    private Double amountPerFee;

    @ApiModelProperty("Cantidad total del préstamo")
    private Double amountLoan;

    @ApiModelProperty("Número de cuotas que faltan por pagar")
    private Integer fee;

    @ApiModelProperty("Número de cuotas del préstamo")
    private Integer feeLoan;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "accountIncome_id")
    @ApiModelProperty("Cuenta de ingreso del préstamo")
    private Account accountInCome;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "accountCollection_id")
    @ApiModelProperty("Cuenta de cobro del préstamo")
    private Account accountCollection;

    @ApiModelProperty("Cuenta de cobro del préstamo")
    private Double interestRate;

    public Loan() {
    }

    public Loan( Double amount, Double amountPerFee, Double amountLoan, Integer fee, Integer feeLoan, Double interestRate) {
        this.amount = amount;
        this.amountPerFee = amountPerFee;
        this.amountLoan = amountLoan;
        this.fee = fee;
        this.feeLoan = feeLoan;
        this.interestRate = interestRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Account getAccountInCome() {
        return accountInCome;
    }

    public void setAccountInCome(Account accountInCome) {
        this.accountInCome = accountInCome;
    }


    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Account getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Account accountCollection) {
        this.accountCollection = accountCollection;
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

    public Integer getFeeLoan() {
        return feeLoan;
    }

    public void setFeeLoan(Integer feeLoan) {
        this.feeLoan = feeLoan;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", amount=" + amount +
                ", amountPerFee=" + amountPerFee +
                ", amountLoan=" + amountLoan +
                ", fee=" + fee +
                '}';
    }
}
