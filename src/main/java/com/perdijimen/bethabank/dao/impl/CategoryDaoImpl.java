package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.CategoryDao;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Category;
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
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Category> findAll() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        Root<Category> root = criteria.from(Category.class);
        criteria.select(root);

        return manager.createQuery(criteria).getResultList();
    }
}

