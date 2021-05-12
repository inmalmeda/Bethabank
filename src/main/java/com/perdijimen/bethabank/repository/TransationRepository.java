package com.perdijimen.bethabank.repository;

import com.perdijimen.bethabank.model.Category;
import com.perdijimen.bethabank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransationRepository extends JpaRepository<Transaction, Long> {
}
