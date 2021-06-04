package com.perdijimen.bethabank.threads;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Loan;
import com.perdijimen.bethabank.services.AccountService;
import com.perdijimen.bethabank.services.LoanService;

import java.util.Optional;

public class LoanThread implements Runnable{

    LoanService loanService;
    AccountService accountService;

    Loan loan;

    public LoanThread(Loan loan, LoanService loanService , AccountService accountService){
        this.loan = loan;
        this.loanService = loanService;
        this.accountService = accountService;
    }

    @Override
    public void run() {
        try {
            int countFee = loan.getFee();
            for (int fee=0; fee<countFee; fee++){
                Thread.sleep(120000);
                loan.setAmount(loan.getAmount() - loan.getAmountPerFee());
                loan.setFee(loan.getFee()-1);
                this.loan = loanService.updateLoan(loan);
                updateAmountInAccount(loan.getAccountCollection().getId());
            }
        }catch (InterruptedException exc){
            System.out.println("Interrumpido.");
        }
        System.out.println("Terminando ");
    }

    private void updateAmountInAccount(Long idAccount){
        Optional<Account> accountCollection = accountService.findById(idAccount);
        accountCollection.get().setTotal_amount(accountCollection.get().getTotal_amount() - loan.getAmountPerFee());
        accountService.updateAccountObject(accountCollection.get());
    }
}
