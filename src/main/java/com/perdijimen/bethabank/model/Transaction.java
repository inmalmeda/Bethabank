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

    @Column
    @ApiModelProperty("Número de cuenta ajena de la que se recibe el ingreso o a la que se envía la transacción")
    private String accountOut;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "account_id")
    @ApiModelProperty("Cuenta con la que se realiza la transacción")
    private Account account;

   @JsonIgnore
   @ManyToOne()
   @JoinColumn(name = "category_id")
   @ApiModelProperty("Categoría a la que pertenece la transacción")
   private Category category;

   @JsonIgnore
   @ManyToOne()
   @JoinColumn(name = "card_id")
   @ApiModelProperty("Tarjeta con la que se realiza la transacción, puede ser de valor nulo")
   private Card card;


    public Transaction() {
    }

    public Transaction(Double amount, LocalDate transaction_date, String accountOut) {
        this.amount = amount;
        this.transaction_date = transaction_date;
        this.accountOut = accountOut;
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

    public String getAccountOut() {
        return accountOut;
    }

    public void setAccountOut(String accountOut) {
        this.accountOut = accountOut;
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
