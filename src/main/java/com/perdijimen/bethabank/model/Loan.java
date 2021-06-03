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

    @ApiModelProperty("Cantidad del préstamo")
    private Double amount;

    @ApiModelProperty("Número de cuotas")
    private Integer fee;

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
    private String interestRate;

    public Loan() {
    }

    public Loan(Long id, Double amount, Integer fee, Account accountInCome, Account accountCollection, String interestRate) {
        this.id = id;
        this.amount = amount;
        this.fee = fee;
        this.accountInCome = accountInCome;
        this.accountCollection = accountCollection;
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


    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public Account getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Account accountCollection) {
        this.accountCollection = accountCollection;
    }
}
