package com.perdijimen.bethabank.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "accounts")
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String IBAN;

    private Double balance;

    private LocalDate created_at;

    private LocalDate utdated_at;

    public Account() {
    }

    public Account(String name, String IBAN, Double balance, LocalDate created_at, LocalDate utdated_at) {
        this.name = name;
        this.IBAN = IBAN;
        this.balance = balance;
        this.created_at = created_at;
        this.utdated_at = utdated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUtdated_at() {
        return utdated_at;
    }

    public void setUtdated_at(LocalDate utdated_at) {
        this.utdated_at = utdated_at;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", balance=" + balance +
                ", created_at=" + created_at +
                ", utdated_at=" + utdated_at +
                '}';
    }
}
