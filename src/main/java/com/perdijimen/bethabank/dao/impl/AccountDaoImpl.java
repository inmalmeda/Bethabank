package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.AccountDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.model.response.AnalyticResponse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Account> findAll(Long idUser, Integer limit, Integer page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        Join<Account, User> rootUser = root.join("userList");
        criteria.select(root).where(builder.equal(rootUser.get("id"), idUser));

        TypedQuery<Account> accountQuery = manager.createQuery(criteria);

        if(limit!=null && page!=null){
            accountQuery.setFirstResult(page);
            accountQuery.setMaxResults(limit);
        }

        return accountQuery.getResultList();
    }

    @Override
    public Optional<Account> findById(Long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);

        criteria.select(root);

        criteria.where(builder.equal(root.get("id"), id));

        return Optional.of(manager.createQuery(criteria).getSingleResult());
    }


}
