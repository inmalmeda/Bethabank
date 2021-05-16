package com.perdijimen.bethabank.model.request;

public class CardRequest {

    private String name_type;

    private String password;

    private Long idAccount;

    private Long idUser;

    public CardRequest() {
    }

    public CardRequest(String name_type, String password, Long idAccount, Long idUser) {
        this.name_type = name_type;
        this.password = password;
        this.idAccount = idAccount;
        this.idUser = idUser;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}

