package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Loan;
import com.perdijimen.bethabank.model.request.LoanRequest;
import com.perdijimen.bethabank.model.response.LoanResponse;
import com.perdijimen.bethabank.repository.LoanRepository;
import com.perdijimen.bethabank.services.AccountService;
import com.perdijimen.bethabank.services.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private LoanRepository loanRepository;
    @Autowired
    private AccountService accountService;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public LoanResponse calculateAndcreateLoan(LoanRequest loan) {
        log.info("Calculate and/or create loan");
        LoanResponse loanResponse = null;
        Loan loanCreated = null;

        if(loan.getAmount() != null && loan.getFee() != null){
            loanResponse = calculateLoan(loan.getAmount(), loan.getFee(), chooseInterest("fijo"));

            if(loan.getTypeAction()){
                loanCreated = createLoan(loan, loanResponse);
            }
            loanResponse.setCreated(loanCreated != null);

        }else{
            log.error("Cannot calculate loan: {} , amount and/or fee are null", loan);
        }
        return loanResponse;
    }

    @Override
    public Loan createLoan(LoanRequest request, LoanResponse response) {

        Loan loanCreated = null;

        Optional<Account> accountInCome = accountService.findById(request.getIdAccountInCome());
        Optional<Account> accountCollection = accountService.findById(request.getIdAccountCollection());

        if (accountInCome.isPresent() && accountCollection.isPresent()) {
            Loan loanToCreate = new Loan(response.getAmount(), response.getAmountPerFee(),
                                    response.getAmount(), response.getFee(), response.getInterestRate());
            accountCollection.get().getLoanCollectionList().add(loanToCreate);
            accountInCome.get().getLoanIncomeList().add(loanToCreate);
            loanToCreate.setAccountCollection(accountCollection.get());
            loanToCreate.setAccountInCome(accountInCome.get());

            try {
                loanCreated = loanRepository.save(loanToCreate);

            } catch(Exception e){
                log.error("Cannot save the loan   ", e);
            }
        }else{
            log.error("The account with id: {} or id : {} don´t exist", request.getIdAccountInCome(), request.getIdAccountCollection());
        }
        return loanCreated;
    }

    private LoanResponse calculateLoan (Double amount, Integer fee,  Double interestRate){

        LoanResponse loanCalculated= new LoanResponse(fee, interestRate);
        loanCalculated.setAmount(amount * interestRate + amount);
        loanCalculated.setAmountPerFee(loanCalculated.getAmount()/fee);

        return loanCalculated;
    }

    private Double chooseInterest (String rate){
        switch (rate){
            case "fijo": return 0.06;
            default: return 0.08;
        }

    }
}
