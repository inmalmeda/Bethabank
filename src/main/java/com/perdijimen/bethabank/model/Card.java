package com.perdijimen.bethabank.model;


import javax.persistence.*;

@Entity
@Table(name = "cards")
public class Card {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String card_number;

    private String CVV;

    private String name_type;

    private String expiration_date;

    private String created_at;

    private String password;

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
