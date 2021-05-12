package com.perdijimen.bethabank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave primaria tipo Long")
    private Long id;

    @Column
    @ApiModelProperty("Nombre del tipo de cuenta")
    private String name;

    @Column
    @ApiModelProperty("Cadena de texto del código IBAN de la cuenta")
    private String IBAN;

    @Column
    @ApiModelProperty("Total de saldo de la cuenta")
    private Double balance;

    @Column
    @ApiModelProperty("Fecha de alta de la cuenta")
    private LocalDate created_at;

    @Column
    @ApiModelProperty("Fecha de última actualización de la cuenta")
    private LocalDate utdated_at;


    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "titular_id")
    private User titularUser;

    @JsonIgnore
    @ManyToMany(mappedBy = "ownersAccountList",fetch = FetchType.LAZY)
    private List<User>  ownersUsers;



    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Transaction> transactionList;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Card> cardList;

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

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public User getTitularUser() {
        return titularUser;
    }

    public void setTitularUser(User titularUser) {
        this.titularUser = titularUser;
    }

    public List<User> getOwnersUsers() {
        return ownersUsers;
    }

    public void setOwnersUsers(List<User> ownersUsers) {
        this.ownersUsers = ownersUsers;
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
