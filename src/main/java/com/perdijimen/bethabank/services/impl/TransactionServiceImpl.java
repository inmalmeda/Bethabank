package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.TransactionDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Category;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.request.TransactionRequest;
import com.perdijimen.bethabank.repository.AccountRepository;
import com.perdijimen.bethabank.repository.CategoryRepository;
import com.perdijimen.bethabank.repository.TransactionRepository;
import com.perdijimen.bethabank.services.AccountService;
import com.perdijimen.bethabank.services.CardService;
import com.perdijimen.bethabank.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private TransactionRepository transactionRepository;
    private AccountService accountService;
    private TransactionDao transactionDao;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CardService cardService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService,
                                  TransactionDao transactionDao) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionDao = transactionDao;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return this.transactionDao.findById(id);
    }

    @Override
    public List<Transaction> findAll(Long idAccount, Boolean type, Integer limit, Integer page) {
        log.debug("Find all transactions");
        return transactionDao.findAll(idAccount, type, limit, page);
    }

    @Override
    public Transaction createTransaction(TransactionRequest transaction) {
        log.info("createTransaction");

        Transaction transactionCreated = null;

        if(transaction.getIdAccount()!=null){
            Optional<Account> account = accountService.findById(transaction.getIdAccount());

            if(account.isPresent()){
                Double totalAmountActual = manageAmountTotalAccount(account, transaction.getAmount(),transaction.getIncome());
                Transaction transactionToCreate = new Transaction(transaction.getAmount(), LocalDate.now(),
                        LocalTime.now(),transaction.getAccountOut(),transaction.getIncome(), totalAmountActual);
                try{
                    account.get().getTransactionList().add(transactionToCreate);
                    transactionToCreate.setAccount(account.get());

                    //Si el movimiento se ha hecho con una cuenta
                    if(transaction.getIdCard()!=null){
                        Optional<Card> card = Optional.empty();

                        //Comprobar que la tarjeta pertenece a la cuenta
                        for (Card cardSearched: account.get().getCardList()) {
                            if(cardSearched.getId() == transaction.getIdCard()){
                                card = cardService.findById(transaction.getIdCard());
                            }
                        }
                        if(card.isPresent()){
                            card.get().getTransactionList().add(transactionToCreate);
                            transactionToCreate.setCard(card.get());
                            cardService.updateCardObject(card.get());
                        }
                    }

                    //Se le añade la categoría a la transacción
                    Optional<Category> category = Optional.empty();

                    if(transaction.getIdCategory() != null){
                        category = categoryRepository.findById(transaction.getIdCategory());
                    }else{
                        category = categoryRepository.findByName("Otros");
                    }

                    transactionToCreate.setCategory(category.get());
                    category.get().getTransactionList().add(transactionToCreate);
                    categoryRepository.save(category.get());
                    accountService.updateAccountObject(account.get());
                    transactionCreated = transactionRepository.save(transactionToCreate);

                }catch(Exception e) {
                    log.error("Cannot save the transaction: {} , error : {}", transaction, e);
                }

            }else{
                log.warn("Account doesn´t exist");
            }
        }

        return transactionCreated;
    }

    @Override
    public boolean deleteTransactionById(Long id) {
        log.debug("Delete a transaction by id: {}", id);

        Transaction transactionToDelete = manager.find(Transaction.class, id);
        if (transactionToDelete != null) {
            try{
               transactionRepository.deleteById(id);

            }catch(Exception e){
                log.error("Cannot delete transaction with id {}", id);
                return false;
            }
        }else {
            log.error("Doesn´t exist transaction with id {}", id);
            return false;
        }
        return true;
    }




    private double manageAmountTotalAccount(Optional<Account> accountToUpdate, Double amount, boolean isIncome){
        Double total = 0.0;

        if (accountToUpdate.isPresent()) {
            total = accountToUpdate.get().getTotal_amount();
            total = isIncome ? total + amount : total - amount;
            accountToUpdate.get().setTotal_amount(total);

            accountService.updateAccountObject(accountToUpdate.get());
        }

        return total;
    }

}
