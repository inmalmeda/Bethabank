package com.perdijimen.bethabank.model.request;

public class AccountUpdateRequest {

    private Long idAccount;

    private Long idUserOwner;

    private Long idCard;

    private Long idUserToAsociateCard;

    public AccountUpdateRequest() {
    }

    public AccountUpdateRequest(Long idAccount, Long idUserOwner, Long idCard, Long idUserToAsociateCard) {
        this.idAccount = idAccount;
        this.idUserOwner = idUserOwner;
        this.idCard = idCard;
        this.idUserToAsociateCard = idUserToAsociateCard;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public Long getIdUserOwner() {
        return idUserOwner;
    }

    public void setIdUserOwner(Long idUserOwner) {
        this.idUserOwner = idUserOwner;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public Long getIdUserToAsociateCard() {
        return idUserToAsociateCard;
    }

    public void setIdUserToAsociateCard(Long idUserToAsociateCard) {
        this.idUserToAsociateCard = idUserToAsociateCard;
    }
}
