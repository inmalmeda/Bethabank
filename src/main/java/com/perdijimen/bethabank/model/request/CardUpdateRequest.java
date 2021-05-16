package com.perdijimen.bethabank.model.request;

public class CardUpdateRequest {

    private Long idCard;

    private String password;

    private Boolean expiration_date;

    public CardUpdateRequest() {
    }

    public CardUpdateRequest(Long idCard, String password, Boolean expiration_date) {
        this.idCard = idCard;
        this.password = password;
        this.expiration_date = expiration_date;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Boolean expiration_date) {
        this.expiration_date = expiration_date;
    }
}


