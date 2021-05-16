package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.dao.CardDao;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.repository.CardRepository;
import com.perdijimen.bethabank.services.CardService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private CardRepository cardRepository;
    private CardDao cardDao;

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
    public Card createCard(Card card) {
        log.info("createCard");

        Card cardCreated = null;

        String cvv = null;
        String cardNumber = null;

        if(card.getId() == null){

            for(int i = 0 ; i<4 ; i++){
                cvv += (int)Math.floor(Math.random()*9);
            }
            card.setCVV(cvv);

            for(int i = 0 ; i<16 ; i++){
                cardNumber += (int)Math.floor(Math.random()*9);
            }
            card.setCard_number(cardNumber);

            String md5HexPassword = DigestUtils.md5Hex(card.getPassword()).toUpperCase();
            card.setPassword(md5HexPassword);

            String md5HexCvv = DigestUtils.md5Hex(card.getCVV()).toUpperCase();
            card.setCVV(md5HexCvv);

            try{
                card.setCreated_at(LocalDate.now());
                card.setUpdated_at(LocalDate.now());
                cardCreated = cardRepository.save(card);
            }catch(Exception e) {
                log.error("Cannot save the card: {} , error : {}", card, e);
            }
        }else{
            log.warn("Creating card with id");
        }

        return cardCreated;
    }

    @Override
    public Card updateCard(Card card) {
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
}
