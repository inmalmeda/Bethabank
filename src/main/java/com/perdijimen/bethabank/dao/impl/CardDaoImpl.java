package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.CardDao;
import com.perdijimen.bethabank.model.Card;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class CardDaoImpl implements CardDao {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Optional<Card> findByIdFromEntityManager(Long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Card> criteria = builder.createQuery(Card.class);
        Root<Card> root = criteria.from(Card.class);

        criteria.select(root);

        criteria.where(builder.equal(root.get("id"), id));

        return Optional.of(manager.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<Card> findAll(Integer limite, Integer pagina) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Card> criteria = builder.createQuery(Card.class);
        Root<Card> root = criteria.from(Card.class);
        criteria.select(root);

        Query query = manager.createQuery(criteria);

        query.setMaxResults(limite); // size
        query.setFirstResult(pagina); // pagination

        List<Card> users = query.getResultList();

        return users;
    }
}
