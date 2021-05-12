package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.AccountDAO;
import com.perdijimen.bethabank.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Optional<Account> findByIdFromEntityManager(Long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);

        criteria.select(root);

        criteria.where(builder.equal(root.get("id"), id));

        return Optional.of(manager.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<Account> findAll(Integer limite, Integer pagina) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root);

        Query query = manager.createQuery(criteria);

        query.setMaxResults(limite); // size
        query.setFirstResult(pagina); // pagination

        List<Account> users = query.getResultList();

        return users;
    }
}
