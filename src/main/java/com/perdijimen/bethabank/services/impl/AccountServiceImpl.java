package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.AccountDao;
import com.perdijimen.bethabank.model.*;
import com.perdijimen.bethabank.model.request.AccountRequest;
import com.perdijimen.bethabank.model.request.AccountUpdateRequest;
import com.perdijimen.bethabank.repository.AccountRepository;
import com.perdijimen.bethabank.services.AccountService;
import com.perdijimen.bethabank.services.CardService;
import com.perdijimen.bethabank.services.TransactionService;
import com.perdijimen.bethabank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private AccountRepository accountRepository;

    private AccountDao accountDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private TransactionService transactionService;


    public AccountServiceImpl(AccountRepository accountRepository, AccountDao accountDao) {
        this.accountRepository = accountRepository;
        this.accountDao = accountDao;
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
    public Account createAccount(AccountRequest account) {
        log.info("createAccount");
        Account accountToCreate = null;

        if(account.getIdUserTitular()!=null){
            Optional<User> userAccount = userService.findById(account.getIdUserTitular());

            if(userAccount.isPresent()){
                accountToCreate = new Account(account.getTypeAccount(), "", 0.0, LocalDate.now(), LocalDate.now());

                //Crear IBAN
                do{
                   accountToCreate.setIBAN(createIBAN());
                }while(accountRepository.findByIBAN(accountToCreate.getIBAN()) != null);

                //Se indica el usuario titular y se inserta dentro de la lista de propietarios de la cuenta
                userAccount.get().getTitularAccountList().add(accountToCreate);
                userAccount.get().getOwnerAccountList().add(accountToCreate);
                //userService.updateUser(userAccount.get());
                accountToCreate.setTitularUser(userAccount.get());
                accountToCreate.setUserList(new ArrayList<>());
                accountToCreate.getUserList().add(userAccount.get());

                //Guardado en bbdd de titular y propietarios de la cuenta
                try{
                    accountToCreate = accountRepository.save(accountToCreate);

                    for(int i = 0; i<account.getIdUserOwnerList().size(); i++){
                        Optional<User> userOwnerAccount = userService.findById(account.getIdUserOwnerList().get(i));

                         if(userOwnerAccount.isPresent() && userOwnerAccount.get() != userAccount.get()){
                                userOwnerAccount.get().getOwnerAccountList().add(accountToCreate);
                                accountToCreate.getUserList().add(userOwnerAccount.get());
                                //userService.updateUser(userOwnerAccount.get());
                                //accountToCreate = accountRepository.save(accountToCreate);
                         }
                    }

                    accountToCreate = updateAccountObject(accountToCreate);

                }catch(Exception e) {
                    log.error("Cannot save the account: {} , error : {}", account, e);
                }
            }
        }

        return accountToCreate;
    }

    @Override
    public Account updateAccount(AccountUpdateRequest account) {
        log.info("updateAccount");
        Account result = null;

        Optional<Account> accountToUpdate = accountRepository.findById(account.getIdAccount());

        if (accountToUpdate.isPresent()) {
            try{
                if(account.getIdUserOwner()!=null){
                    Optional<User> userOwnerAccount = userService.findById(account.getIdUserOwner());
                    updateListAccountOfUsers(userOwnerAccount, accountToUpdate);
                }

               if(account.getIdCard() != null && account.getIdUserToAsociateCard() != null){

                    Optional<Card> cardToAccount = cardService.findById(account.getIdCard());
                    Optional<User> userToCard = userService.findById(account.getIdUserToAsociateCard());

                    if(cardToAccount.isPresent() && userToCard.isPresent()){
                        Optional<User> userInAccount = Optional.empty();

                        //Se comprueba que el usuario sea uno de los propietarios de la cuenta
                        for (User user:  accountToUpdate.get().getUserList()) {
                            userInAccount = accountToUpdate.get().getUserList().stream()
                                    .filter(c -> Objects.equals( user.getId(), c.getId())).findFirst();
                        }

                        if(userInAccount.isPresent()){
                            accountToUpdate.get().getCardList().add(cardToAccount.get());
                            cardToAccount.get().setAccount(accountToUpdate.get());
                            cardToAccount.get().setUser(userToCard.get());
                            userToCard.get().getCardList().add(cardToAccount.get());
                            userService.updateUser(userToCard.get());
                            cardService.updateCardObject(cardToAccount.get());
                        }
                    }
                }

                accountToUpdate.get().setUpdated_at(LocalDate.now());
                result = accountRepository.save(accountToUpdate.get());

            }catch(Exception e){
                log.error("Cannot save account: {} , error : {}", account, e);
            }
        }else{
            log.warn("Cannot save account: {}, because it doesn´t exist", account);
        }
        return result;
    }


    @Override
    public Account updateAccountObject(Account account) {
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

    @Override
    public boolean deleteAccountById(Long id) {
        log.debug("Delete an account by id: {}", id);

        Account accountToDelete = manager.find(Account.class, id);
        if (accountToDelete != null) {
            try{

                for (Card card: accountToDelete.getCardList()) {
                    cardService.deleteCardById(card.getId());
                }

                for(Transaction tran : accountToDelete.getTransactionList()){
                    transactionService.deleteTransactionById(tran.getId());
                }

                for(User user: accountToDelete.getUserList()){
                    List<Account> accountList = new ArrayList<>();
                    for(Account account: user.getOwnerAccountList()){
                        if(account.getId() != id){
                            accountList.add(account);
                        }
                    }
                    user.setOwnerAccountList(accountList);
                    userService.updateUser(user);
                }

                accountRepository.deleteById(id);

            }catch(Exception e){
                log.error("Cannot delete account with id {}", id);
                return false;
            }
        }else {
            log.error("Doesn´t exist account with id {}", id);
            return false;
        }

        return true;
    }

    private String createIBAN (){
        String iban = "ES ";

        for(int i = 0 ; i<22 ; i++){
            iban += (int)Math.floor(Math.random()*9);
        }
        return iban;
    }

    private void updateListAccountOfUsers(Optional<User> userOwnerAccount,  Optional<Account> accountToUpdate){

        if(userOwnerAccount.isPresent() && userOwnerAccount.get() != accountToUpdate.get().getTitularUser()){
            accountToUpdate.get().getUserList().add(userOwnerAccount.get());
            userOwnerAccount.get().getOwnerAccountList().add(accountToUpdate.get());
            userService.updateUser(userOwnerAccount.get());
        }
    }

}
