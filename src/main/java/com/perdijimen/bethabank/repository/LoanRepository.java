package com.perdijimen.bethabank.repository;

import com.perdijimen.bethabank.model.Category;
import com.perdijimen.bethabank.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
