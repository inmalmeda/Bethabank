package com.perdijimen.bethabank.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perdijimen.bethabank.model.Account;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class LoanRequest {

    private Double amount;

    private Integer fee;

    private Long idAccountInCome;

    private Long idAccountCollection;

    private Boolean typeAction;

    public LoanRequest() {
    }


    public LoanRequest(Double amount, Integer fee, Long idAccountInCome, Long idAccountCollection, Boolean typeAction) {
        this.amount = amount;
        this.fee = fee;
        this.idAccountInCome = idAccountInCome;
        this.idAccountCollection = idAccountCollection;
        this.typeAction = typeAction;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Long getIdAccountInCome() {
        return idAccountInCome;
    }

    public void setIdAccountInCome(Long idAccountInCome) {
        this.idAccountInCome = idAccountInCome;
    }

    public Long getIdAccountCollection() {
        return idAccountCollection;
    }

    public void setIdAccountCollection(Long idAccountCollection) {
        this.idAccountCollection = idAccountCollection;
    }

    public Boolean getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(Boolean typeAction) {
        this.typeAction = typeAction;
    }
}
