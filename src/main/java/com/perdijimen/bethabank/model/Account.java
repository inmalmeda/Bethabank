package com.perdijimen.bethabank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate updated_at;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "titular_id")
    @ApiModelProperty("Titular de la cuenta")
    private User titularUser;


    @JsonIgnore
    @ManyToMany(mappedBy = "ownerAccountList", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @ApiModelProperty("Lista de usuarios que pertenecen a la cuenta")
    private List<User> userList;

    @OneToMany(mappedBy = "account")
    @ApiModelProperty("Lista de movimientos realizados con la cuenta")
     private List<Transaction> transactionList;

    @OneToMany(mappedBy = "account")
    @ApiModelProperty("Lista de tarjetas asociadas a la cuenta")
     private List<Card> cardList;

    public Account() {
    }

    public Account(String name, String IBAN, Double balance, LocalDate created_at, LocalDate utdated_at) {
        this.name = name;
        this.IBAN = IBAN;
        this.balance = balance;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getTitularUser() {
        return titularUser;
    }

    public void setTitularUser(User titularUser) {
        this.titularUser = titularUser;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", balance=" + balance +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
