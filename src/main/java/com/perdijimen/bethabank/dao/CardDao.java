package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardDao {

    Optional<Card> findByIdFromEntityManager(Long id);
    List<Card> findAll(Integer limite, Integer pagina);
}
