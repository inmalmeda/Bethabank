package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.repository.CardRepository;
import com.perdijimen.bethabank.services.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Service
public class CardServiceImpl implements CardService {

    private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;

    private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(Card card) {
        log.info("createCard");

        Card cardCreated = null;

        if(card.getId() == null){
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
