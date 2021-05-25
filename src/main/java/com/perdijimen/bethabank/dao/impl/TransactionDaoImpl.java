package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.TransactionDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.*;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Transaction> findById(Long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
        Root<Transaction>root = criteria.from(Transaction.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), id));

        return Optional.of(manager.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<Transaction> findAll(Long idAccount, Boolean type, Integer limit, Integer page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
        Root<Transaction> root = criteria.from(Transaction.class);
        Join<Transaction, Account> rootAccount= root.join("account");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(rootAccount.get("id"), idAccount));

        if(type!=null){
            predicates.add(builder.equal(root.get("isIncome"), type));
        }

        criteria.select(root).where(builder.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Transaction> cardsQuery = manager.createQuery(criteria);

        if(limit!=null && page!=null){
            cardsQuery.setFirstResult(page);
            cardsQuery.setMaxResults(limit);
        }

        return cardsQuery.getResultList();
    }

    @Override
    public List<Transaction> getAnalyticTransactions(Long idAccount, LocalDate start, LocalDate end) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
        Root<Transaction> root = criteria.from(Transaction.class);
        Join<Transaction, Account> rootAccount= root.join("account");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(rootAccount.get("id"), idAccount));
        predicates.add(builder.between(root.get("transaction_date"), start, end));

        criteria.select(root).where(builder.and(predicates.toArray(new Predicate[0])));
        criteria.orderBy(builder.asc(root.get("transaction_date")));

        return manager.createQuery(criteria).getResultList();
    }

    @Override
    public List<Transaction> getAnalyticTransactions(Long id, Boolean isAccount, LocalDate start, LocalDate end) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
        Root<Transaction> root = criteria.from(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();

        if(isAccount){
            Join<Transaction, Account> rootAccount= root.join("account");
            predicates.add(builder.equal(rootAccount.get("id"), id));
        }else{
            Join<Transaction, Card> rootCard= root.join("card");
            predicates.add(builder.equal(rootCard.get("id"), id));
        }

        predicates.add(builder.between(root.get("transaction_date"), start.minusDays(1), end.plusDays(1)));
        criteria.select(root).where(builder.and(predicates.toArray(new Predicate[0])));
        criteria.orderBy(builder.asc(root.get("transaction_date")));

        return manager.createQuery(criteria).getResultList();
    }

    @Override
    public List<Transaction> getAnalyticTransactionsUser(List<Account> accountList, LocalDate start, LocalDate end) {

        List<Transaction> transactionList = new ArrayList<>();

        if(!accountList.isEmpty()){

            for (Account account: accountList) {
                CriteriaBuilder builder = manager.getCriteriaBuilder();
                CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
                Root<Transaction> root = criteria.from(Transaction.class);
                List<Predicate> predicates = new ArrayList<>();
                Join<Transaction, Account> rootAccount= root.join("account");
                predicates.add(builder.equal(rootAccount.get("id"), account.getId()));
                predicates.add(builder.between(root.get("transaction_date"), start.minusDays(1), end.plusDays(1)));
                criteria.select(root).where(builder.and(predicates.toArray(new Predicate[0])));
                criteria.orderBy(builder.asc(root.get("transaction_date")));

                List<Transaction> transactions = manager.createQuery(criteria).getResultList();

                for (Transaction transaction: transactions ) {
                    transactionList.add(transaction);
                }
            }
        }

        Collections.sort(transactionList, Comparator.comparing(Transaction::getTransaction_date));

       return transactionList;
    }

    @Override
    public List<Transaction> getAnalyticTransactionsCategory(Long idAccount, LocalDate start) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
        Root<Transaction> root = criteria.from(Transaction.class);
        Join<Transaction, Account> rootAccount= root.join("account");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(rootAccount.get("id"), idAccount));
        predicates.add(builder.equal(root.get("isIncome"), false));
        predicates.add(builder.greaterThan(root.get("transaction_date"), start));

        criteria.select(root).where(builder.and(predicates.toArray(new Predicate[0])));
        criteria.orderBy(builder.asc(root.get("transaction_date")));

        return manager.createQuery(criteria).getResultList();
    }
}

