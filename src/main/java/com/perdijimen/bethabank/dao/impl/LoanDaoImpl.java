package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.LoanDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LoanDaoImpl implements LoanDao {

    @PersistenceContext
    private EntityManager manager;
}

