package com.perdijimen.bethabank.dao;

import com.perdijimen.bethabank.model.Loan;

import java.util.List;

public interface LoanDao {

    List<Loan> findAll(Long idUser, Integer limite, Integer pagina);
}
