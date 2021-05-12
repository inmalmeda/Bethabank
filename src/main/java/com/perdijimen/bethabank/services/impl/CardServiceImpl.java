package com.perdijimen.bethabank.services.impl;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.repository.CardRepository;
import com.perdijimen.bethabank.services.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CardServiceImpl implements CardService {

    private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    @PersistenceContext
    private EntityManager manager;


    CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(Card card) {
        log.info("createCard");
        if (ObjectUtils.isEmpty(card))
            return null;

        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(Card card) {
        log.info("updateCard");
        if (ObjectUtils.isEmpty(card))
            return null;
        return cardRepository.save(card);
    }
}
