package com.perdijimen.bethabank.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave primaria tipo Long")
    private Long id;

    @Column
    @ApiModelProperty("Cadena de texto del número de tarjeta")
    private String card_number;

    @Column
    @ApiModelProperty("Cadena de texto del código CVV de tarjeta")
    private String CVV;

    @Column
    @ApiModelProperty("Tipo de tarjeta: crédito, débito, corriente")
    private String name_type;

    @Column
    @ApiModelProperty("Fecha de caducidad de la tarjeta")
    private String expiration_date;

    @Column
    @ApiModelProperty("Fecha de creación de la tarjeta")
    private String created_at;

    @Column
    @ApiModelProperty("Contraseña de la tarjeta")
    private String password;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cards", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionList = new ArrayList();

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    public Card() {
    }

    public Card(String card_number, String CVV, String name_type, String expiration_date, String created_at, String password) {
        this.card_number = card_number;
        this.CVV = CVV;
        this.name_type = name_type;
        this.expiration_date = expiration_date;
        this.created_at = created_at;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", card_number='" + card_number + '\'' +
                ", CVV='" + CVV + '\'' +
                ", name_type='" + name_type + '\'' +
                ", expiration_date='" + expiration_date + '\'' +
                ", created_at='" + created_at + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
