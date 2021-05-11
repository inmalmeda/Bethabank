package com.perdijimen.bethabank.model;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double amount;

    @Column
    private LocalDate transction_date;

    public Transaction() {
    }

    public Transaction(Double amount, LocalDate transction_date) {
        this.amount = amount;
        this.transction_date = transction_date;
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

    public LocalDate getTransction_date() {
        return transction_date;
    }

    public void setTransction_date(LocalDate transction_date) {
        this.transction_date = transction_date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", transction_date=" + transction_date +
                '}';
    }
}
