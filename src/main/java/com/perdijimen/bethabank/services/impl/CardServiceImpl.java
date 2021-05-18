package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.CardDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.model.request.AccountUpdateRequest;
import com.perdijimen.bethabank.model.request.CardRequest;
import com.perdijimen.bethabank.model.request.CardUpdateRequest;
import com.perdijimen.bethabank.repository.CardRepository;
import com.perdijimen.bethabank.repository.TransactionRepository;
import com.perdijimen.bethabank.services.AccountService;
import com.perdijimen.bethabank.services.CardService;
import com.perdijimen.bethabank.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private CardRepository cardRepository;
    private CardDao cardDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository transactionRepository;

    public CardServiceImpl(CardRepository cardRepository, CardDao cardDao) {
        this.cardRepository = cardRepository;
        this.cardDao = cardDao;
    }

    @Override
    public List<Card> findAll(Long idUser, Integer limit, Integer page) {
        log.debug("Find all cards");
        return cardDao.findAll(idUser, limit, page);
    }

    @Override
    public Optional<Card> findById(Long id) {
        log.debug("Find one card by id: {}", id);
        return cardDao.findById(id);
    }

    @Override
    public Card createCard(CardRequest card) {
        log.info("createCard");
        Card cardCreated = null;

        Card cardToCreate = new Card(generateNumberCard(), generateCVV(), card.getName_type(), generateExpiratedDate(),
                LocalDate.now(),LocalDate.now(), generatePassword(card.getPassword()));

        try{
            Optional<Account>account = accountService.findById(card.getIdAccount());
            Optional<User> user = Optional.empty();

            for (User userSearched: account.get().getUserList()) {
                if(userSearched.getId() == card.getIdUser()){
                    user = userService.findById(card.getIdUser());
                }
            }

            if(account.isPresent() && user.isPresent()){
                account.get().getCardList().add(cardToCreate);
                user.get().getCardList().add(cardToCreate);
                cardToCreate.setAccount(account.get());
                cardToCreate.setUser(user.get());
                cardCreated = cardRepository.save(cardToCreate);
                userService.updateUser(user.get());
                accountService.updateAccount(new AccountUpdateRequest(card.getIdAccount(), cardCreated.getId(), card.getIdUser()));

            }else{
                log.error("Cannot save the card: {} , error : {}", card);
            }
        }catch(Exception e) {
            log.error("Cannot save the card: {} , error : {}", card, e);
        }

        return cardCreated;
    }

    @Override
    public Card updateCard(CardUpdateRequest card) {
        log.info("updateCard");
        Card result = null;

        Optional<Card> cardToUpdate = cardRepository.findById(card.getIdCard());

        if(cardToUpdate.isPresent()){
            try{
                if(card.getPassword() != null){
                    cardToUpdate.get().setPassword(generatePassword(card.getPassword()));
                }

                if(card.getExpiration_date()){
                    cardToUpdate.get().setExpiration_date(generateExpiratedDate());
                }

                cardToUpdate.get().setUpdated_at(LocalDate.now());
                result = cardRepository.save(cardToUpdate.get());
            } catch(Exception e){
                log.error("Cannot save card: {} , error : {}", card, e);
            }
        }else{
            log.warn("Cannot save card: {}, because it doesn´t exist", card);
        }

        return result;
    }

    @Override
    public Card updateCardObject(Card card) {
        log.info("updateCard");

        Card result = null;

        if (cardRepository.existsById(card.getId())) {
            try{
                card.setUpdated_at(LocalDate.now());
                result = cardRepository.save(card);
            }catch(Exception e){
                log.error("Cannot save card: {} , error : {}", card, e);
            }
        }else{
            log.warn("Cannot save card: {}, because it doesn´t exist", card);
        }
        return result;
    }

    @Override
    public boolean deleteCardById(Long id) {
        log.debug("Delete a card by id: {}", id);

        Card cardToDelete = manager.find(Card.class, id);
        if (cardToDelete != null) {
            try{
                List<Transaction> transactionToDelete = cardToDelete.getTransactionList();
                for(Transaction tran : transactionToDelete){
                    tran.setCard(null);
                    transactionRepository.save(tran);
                }

                Account account = manager.find(Account.class, cardToDelete.getAccount().getId());
                List<Card> cardListForAccount = new ArrayList<>();
                for (Card card:  account.getCardList() ) {
                    if(card.getId() != id){
                        cardListForAccount.add(card);
                    }
                }
                account.setCardList(cardListForAccount);
                accountService.updateAccountObject(account);

                cardRepository.deleteById(id);

            }catch(Exception e){
                log.error("Cannot delete card with id {}", id, "*********" , e);
                return false;
            }
        }else {
            log.error("Doesn´t exist card with id {}", id);
            return false;
        }

        return true;
    }

    private String generateNumberCard(){
        String cardNumber = "";
        for(int i = 0 ; i<16 ; i++){
            cardNumber += (int)Math.floor(Math.random()*9);
        }
        return cardNumber;
    }


    private String generateCVV(){
        String cvv = "";
        for(int i = 0 ; i<4 ; i++){
            cvv += (int)Math.floor(Math.random()*9);
        }

        return DigestUtils.md5Hex(cvv).toUpperCase();
    }

    private LocalDate generateExpiratedDate (){
        return LocalDate.now().plusYears(10);
    }

    private String generatePassword (String password){
        return  DigestUtils.md5Hex(password).toUpperCase();
    }

}
