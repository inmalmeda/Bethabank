package com.perdijimen.bethabank.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave primaria tipo Long")
    private Long id;

    @Column
    @ApiModelProperty("Cantidad de la transacción")
    private Double amount;

    @Column
    @ApiModelProperty("Fecha de la transacción")
    private LocalDate transaction_date;


    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;


   @JsonIgnore
   @ManyToOne()
   @JoinColumn(name = "category_id")
   private Category category;

   @JsonIgnore
   @ManyToOne()
   @JoinColumn(name = "card_id")
   private Card card;



    public Transaction() {
    }

    public Transaction(Double amount, LocalDate transaction_date) {
        this.amount = amount;
        this.transaction_date = transaction_date;
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

    public LocalDate getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDate transaction_date) {
        this.transaction_date = transaction_date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", transaction_date=" + transaction_date +

                '}';
    }
}
