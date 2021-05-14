package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAll();
}
