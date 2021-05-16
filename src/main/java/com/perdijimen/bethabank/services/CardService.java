package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.request.CardRequest;

import java.util.List;
import java.util.Optional;

public interface CardService {

    List<Card> findAll(Long idUser, Integer limit, Integer page);

    Optional<Card> findById(Long id);

    Card createCard(CardRequest card);

    Card updateCard(Card card);
}
