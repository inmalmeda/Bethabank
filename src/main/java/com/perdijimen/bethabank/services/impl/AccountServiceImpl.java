package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.AccountDao;
import com.perdijimen.bethabank.dao.TransactionDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.response.AnalyticResponse;
import com.perdijimen.bethabank.repository.AccountRepository;
import com.perdijimen.bethabank.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private AccountRepository accountRepository;

    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public AccountServiceImpl(AccountRepository accountRepository, AccountDao accountDao,
                              TransactionDao transactionDao) {
        this.accountRepository = accountRepository;
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return this.accountDao.findById(id);
    }

    @Override
    public List<Account> findAll(Long idUser, Integer limit, Integer page) {
        return this.accountDao.findAll(idUser, limit, page);
    }

    @Override
    public List<AnalyticResponse> getAnalytics (Long idAccount, Boolean typePeriod){
        LocalDate startDate;
        LocalDate endDate;
        int year = LocalDate.now().getYear();
        List<AnalyticResponse> analytic = new ArrayList<>();

        if(idAccount != null && accountDao.findById(idAccount).isPresent()){
            //Si es tipo mes
            if(typePeriod){
                startDate = LocalDate.of(year, 1, 1);
                endDate = LocalDate.of(year, 12, 31);
            }else{
                int startYear = accountDao.findById(idAccount).get().getCreated_at().getYear();
                startDate =  LocalDate.of(startYear, 1, 31);
                endDate = LocalDate.of(year, 12, 31);
            }
            List<Transaction> transactionList = transactionDao.getAnalyticTransactions(idAccount, startDate, endDate);

            analytic = typePeriod ? monthAnalytics(transactionList) : yearAnalytics(transactionList);
        }
        return analytic;
    }

    @Override
    public Account createAccount(Account account) {
        log.info("createAccount");

        Account accountCreated = null;

        if(account.getId() == null){
            try{
                account.setCreated_at(LocalDate.now());
                account.setUpdated_at(LocalDate.now());
                accountCreated = accountRepository.save(account);
            }catch(Exception e) {
                log.error("Cannot save the account: {} , error : {}", account, e);
            }
        }else{
            log.warn("Creating account with id");
        }

        return accountCreated;
    }

    @Override
    public Account updateAccount(Account account) {
        log.info("updateAccount");

        Account result = null;

        if (accountRepository.existsById(account.getId())) {
            try{
                account.setUpdated_at(LocalDate.now());
                result = accountRepository.save(account);
            }catch(Exception e){
                log.error("Cannot save account: {} , error : {}", account, e);
            }
        }else{
            log.warn("Cannot save account: {}, because it doesn´t exist", account);
        }
        return result;
    }

    private List<AnalyticResponse> monthAnalytics ( List<Transaction> transactionList){

        List<AnalyticResponse> analytic = new ArrayList<>();

        if(!transactionList.isEmpty()){
            //Selección mes de comienzo del analytics
            int monthSelect = transactionList.get(0).getTransaction_date().getMonthValue();
            analytic.add(new AnalyticResponse(LocalDate.of(transactionList.get(0).getTransaction_date().getYear() ,
                    monthSelect , 1),0.0, 0.0));
            for (int i = 0; i<transactionList.size(); i++) {
                //Si el mes seleccionado es igual que el mes de la transacción
                if(monthSelect == transactionList.get(i).getTransaction_date().getMonthValue()){
                    if(transactionList.get(i).getIncome()){
                        analytic.get(analytic.size()-1).setInCome(analytic.get(analytic.size()-1).getInCome() + transactionList.get(i).getAmount());
                    }else{
                        analytic.get(analytic.size()-1).setExpense(analytic.get(analytic.size()-1).getExpense() + transactionList.get(i).getAmount());
                    }
                }else{
                    monthSelect = transactionList.get(i).getTransaction_date().getMonthValue();

                    if (transactionList.get(i).getIncome()){
                        analytic.add(new AnalyticResponse(LocalDate.of(transactionList.get(i).getTransaction_date().getYear() ,
                                monthSelect , 1),transactionList.get(i).getAmount(), 0.0));
                    }else{
                        analytic.add(new AnalyticResponse(LocalDate.of(transactionList.get(i).getTransaction_date().getYear() ,
                                monthSelect , 1),0.0, transactionList.get(i).getAmount()));
                    }
                }
            }
        }
        return analytic;
    }

    private List<AnalyticResponse> yearAnalytics (List<Transaction> transactionList){

        List<AnalyticResponse> analytic = new ArrayList<>();

        if(!transactionList.isEmpty()){
            //Selección año de comienzo del analytics
            int yearSelect = transactionList.get(0).getTransaction_date().getYear();
            analytic.add(new AnalyticResponse(LocalDate.of(yearSelect,1 , 1),0.0, 0.0));
            for (int i = 0; i<transactionList.size(); i++) {
                //Si el año seleccionado es igual que el año de la transacción
                if(yearSelect == transactionList.get(i).getTransaction_date().getYear()){
                    if(transactionList.get(i).getIncome()){
                        analytic.get(analytic.size()-1).setInCome(analytic.get(analytic.size()-1).getInCome() + transactionList.get(i).getAmount());
                    }else{
                        analytic.get(analytic.size()-1).setExpense(analytic.get(analytic.size()-1).getExpense() + transactionList.get(i).getAmount());
                    }
                }else{
                    yearSelect = transactionList.get(i).getTransaction_date().getYear();

                    if (transactionList.get(i).getIncome()){
                        analytic.add(new AnalyticResponse(LocalDate.of(yearSelect ,
                                1 , 1),transactionList.get(i).getAmount(), 0.0));
                    }else{
                        analytic.add(new AnalyticResponse(LocalDate.of(yearSelect ,
                                1 , 1),0.0, transactionList.get(i).getAmount()));
                    }
                }
            }
        }
        return analytic;
    }
}
