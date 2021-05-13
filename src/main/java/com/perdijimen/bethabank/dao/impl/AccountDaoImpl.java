package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.AccountDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.User;

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

public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Optional<Account> findById(Long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);

        criteria.select(root);

        criteria.where(builder.equal(root.get("id"), id));

        return Optional.of(manager.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<Account> findAll(Long idUser, Integer limit, Integer page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        Join<Account, User> rootUser = root.join("user");
        criteria.select(root).where(builder.equal(rootUser.get("id"), idUser));

        TypedQuery<Account> accountQuery = manager.createQuery(criteria);

        if(limit!=null && page!=null){
            accountQuery.setFirstResult(page);
            accountQuery.setMaxResults(limit);
        }

        return accountQuery.getResultList();
    }

    @Override
    public List<Account> findAllByName(String name, Integer limit, Integer page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root);

        criteria.where(builder.equal(root.get("name"), name));

        Query query = manager.createQuery(criteria);

        query.setMaxResults(limit); // size
        query.setFirstResult(page); // pagination


        return query.getResultList();
    }
}
