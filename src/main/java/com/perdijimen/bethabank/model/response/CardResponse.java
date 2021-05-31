package com.perdijimen.bethabank.model.response;

import java.time.LocalDate;

public class CardResponse {

    private Long id;

    private String card_number;

    private String name_type;

    private LocalDate expiration_date;

    private LocalDate created_at;

    private LocalDate updated_at;

    private AccountOfCardResponse account;

    public CardResponse(Long id, String card_number, String name_type, LocalDate expiration_date, LocalDate created_at, LocalDate updated_at, AccountOfCardResponse account) {
        this.id = id;
        this.card_number = card_number;
        this.name_type = name_type;
        this.expiration_date = expiration_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.account = account;
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

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
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

    public AccountOfCardResponse getAccount() {
        return account;
    }

    public void setAccount(AccountOfCardResponse account) {
        this.account = account;
    }
}
