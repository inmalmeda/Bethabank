package com.perdijimen.bethabank.repository;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
