package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.LoanDao;
import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Loan;
import com.perdijimen.bethabank.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class LoanDaoImpl implements LoanDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Loan> findAll(Long idAccount, Integer limit, Integer page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Loan> criteria = builder.createQuery(Loan.class);
        Root<Loan> root = criteria.from(Loan.class);
        if (idAccount != null) {
            Join<Loan, Account> rootAccount = root.join("accountInCome");
            criteria.select(root).where(builder.equal(rootAccount.get("id"), idAccount));
        }

        TypedQuery<Loan> loanQuery = manager.createQuery(criteria);

        if(limit!=null && page!=null){
            loanQuery.setFirstResult(page);
            loanQuery.setMaxResults(limit);
        }

        return loanQuery.getResultList();
    }
}

