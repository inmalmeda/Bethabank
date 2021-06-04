package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.LoanDao;
import com.perdijimen.bethabank.threads.LoanThread;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Loan;
import com.perdijimen.bethabank.model.request.LoanRequest;
import com.perdijimen.bethabank.model.response.LoanGetAllResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private LoanRepository loanRepository;
    private LoanDao loanDao;
    @Autowired
    private AccountService accountService;

    public LoanServiceImpl(LoanRepository loanRepository, LoanDao loanDao) {
        this.loanRepository = loanRepository;
        this.loanDao = loanDao;
    }

    @Override
    public List<LoanGetAllResponse> findAll(Long idUser, Integer limit, Integer page) {
        log.debug("Find all loans");

        List<Account> accountList = accountService.findAll(idUser,20, 0);
        List<Loan> loanList = new ArrayList<>();
        List<LoanGetAllResponse> response = new ArrayList<>();

        for (Account account: accountList) {
            List<Loan> list = loanDao.findAll(account.getId(), 20, 0);

            if(!list.isEmpty()){
                for(Loan loan : list){
                    loanList.add(loan);
                }
            }
        }

        if(!loanList.isEmpty()){
            response = new ArrayList<>();
            for(Loan loan : loanList){
                response.add(transformResponse(loan));
            }
        }
        return response;
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
            if(loanCreated != null){
                Optional<Account> accountInCome = accountService.findById(loan.getIdAccountInCome());
                accountInCome.get().setTotal_amount(accountInCome.get().getTotal_amount() + loan.getAmount());
                manageLoan(loanCreated);
            }

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
                                    response.getAmount(), response.getFee(), response.getFee(),response.getInterestRate());
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

    @Override
    public Loan updateLoan(Loan loan) {
        log.info("updateLoan");

        Loan result = null;

        if (loanRepository.existsById(loan.getId())) {
            try{
                result = loanRepository.save(loan);
            }catch(Exception e){
                log.error("Cannot save loan: {} , error : {}", loan, e);
            }
        }else{
            log.warn("Cannot save card: {}, because it doesn´t exist", loan);
        }
        return result;
    }

    @Override
    public void manageLoan (Loan loan){
        LoanThread mh=new LoanThread(loan, this, accountService);
        Thread loanthread=new Thread(mh);

        loanthread.start();
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

    private LoanGetAllResponse transformResponse (Loan loan){
        return new LoanGetAllResponse(loan.getAmount(),loan.getAmountPerFee(), loan.getAmountLoan(),
                loan.getFee(), loan.getFeeLoan(), loan.getInterestRate(), loan.getAccountInCome().getIBAN(),
                loan.getAccountCollection().getIBAN());
    }
}
