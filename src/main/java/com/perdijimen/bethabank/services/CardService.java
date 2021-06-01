package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.request.CardRequest;
import com.perdijimen.bethabank.model.request.CardUpdateRequest;
import com.perdijimen.bethabank.model.response.card.CardResponse;

import java.util.List;
import java.util.Optional;

public interface CardService {

    List<CardResponse> findAll(Long idUser, Integer limit, Integer page);

    Optional<Card> findById(Long id);

    Card createCard(CardRequest card);

    Card updateCard(CardUpdateRequest card);

    Card updateCardObject(Card card);

    boolean deleteCardById(Long id);
}
