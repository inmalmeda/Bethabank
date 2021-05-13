package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Card;

import java.util.List;

public interface CardService {

    List<Card> findAll(Long idUser, Integer limit, Integer page);

    Card findById(Long id);

    Card createCard(Card card);

    Card updateCard(Card card);
}
