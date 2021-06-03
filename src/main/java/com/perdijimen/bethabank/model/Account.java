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
    private Double total_amount;

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
    @ManyToMany(mappedBy = "ownerAccountList", fetch = FetchType.EAGER)
    @ApiModelProperty("Lista de usuarios que pertenecen a la cuenta")
    private List<User> userList;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    @ApiModelProperty("Lista de movimientos realizados con la cuenta")
     private List<Transaction> transactionList;

    @OneToMany(mappedBy = "account")
    @ApiModelProperty("Lista de tarjetas asociadas a la cuenta")
     private List<Card> cardList;

    @OneToMany(mappedBy = "accountInCome")
    @ApiModelProperty("Lista de préstamos que se ingresan en la cuenta")
    private List<Loan> loanIncomeList;

    @OneToMany(mappedBy = "accountCollection")
    @ApiModelProperty("Lista de préstamos que se cobran de la cuenta")
    private List<Loan> loanCollectionList;

    public Account() {
    }

    public Account(String name, String IBAN, Double balance, LocalDate created_at, LocalDate updated_at) {
        this.name = name;
        this.IBAN = IBAN;
        this.total_amount = balance;
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

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
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

    public List<Loan> getLoanIncomeList() {
        return loanIncomeList;
    }

    public void setLoanIncomeList(List<Loan> loanIncomeList) {
        this.loanIncomeList = loanIncomeList;
    }

    public List<Loan> getLoanCollectionList() {
        return loanCollectionList;
    }

    public void setLoanCollectionList(List<Loan> loanCollectionList) {
        this.loanCollectionList = loanCollectionList;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", balance=" + total_amount +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", titularUser=" + titularUser +
                ", userList=" + userList +
                ", transactionList=" + transactionList +
                ", cardList=" + cardList +
                '}';
    }
}
