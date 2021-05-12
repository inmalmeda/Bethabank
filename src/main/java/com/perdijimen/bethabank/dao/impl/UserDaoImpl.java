package com.perdijimen.bethabank.dao.impl;

import com.perdijimen.bethabank.dao.UserDao;
import com.perdijimen.bethabank.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<User> findByIdFromEntityManager(Long id){

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User>root = criteria.from(User.class);

        criteria.select(root);

        criteria.where(builder.equal(root.get("id"), id));

        return Optional.of(manager.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<User> findAll(Integer limite, Integer pagina) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);

        Query query = manager.createQuery(criteria);

        query.setMaxResults(limite); // size
        query.setFirstResult(pagina); // pagination

        List<User> users = query.getResultList();

        return users;
    }
}
