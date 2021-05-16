package com.perdijimen.bethabank.model.request;

import com.perdijimen.bethabank.model.Account;

import java.util.List;

public class AccountRequest {

    private String typeAccount;

    private Long idUserTitular;

    private List<Long> idUserOwnerList;

    public AccountRequest() {
    }


    public AccountRequest(String typeAccount, Long idUserTitular, List<Long> idUserOwnerList) {
        this.typeAccount = typeAccount;
        this.idUserTitular = idUserTitular;
        this.idUserOwnerList = idUserOwnerList;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public Long getIdUserTitular() {
        return idUserTitular;
    }

    public void setIdUserTitular(Long idUserTitular) {
        this.idUserTitular = idUserTitular;
    }

    public List<Long> getIdUserOwnerList() {
        return idUserOwnerList;
    }

    public void setIdUserOwnerList(List<Long> idUserOwnerList) {
        this.idUserOwnerList = idUserOwnerList;
    }
}
