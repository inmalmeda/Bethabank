package com.perdijimen.bethabank.model.request;

import com.perdijimen.bethabank.model.Transaction;

public class TransactionRequest {

    private Transaction transaction;

    private Long idAccount;

    public TransactionRequest() {
    }

    public TransactionRequest(Transaction transaction, Long idAccount) {
        this.transaction = transaction;
        this.idAccount = idAccount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }
}
