package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.CardDao;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.User;
import io.swagger.models.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class CardDaoImpl implements CardDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Card> findById(Long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Card> criteria = builder.createQuery(Card.class);
        Root<Card> root = criteria.from(Card.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), id));
        return Optional.of(manager.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<Card> findAll(Long idUser, Integer limit, Integer page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Card> criteria = builder.createQuery(Card.class);
        Root<Card> root = criteria.from(Card.class);
        if (idUser != null) {
            Join<Card, User> rootUser = root.join("user");
            criteria.select(root).where(builder.equal(rootUser.get("id"), idUser));
        }

        TypedQuery<Card> cardsQuery = manager.createQuery(criteria);

        if(limit!=null && page!=null){
            cardsQuery.setFirstResult(page);
            cardsQuery.setMaxResults(limit);
        }

        return cardsQuery.getResultList();
    }
}
