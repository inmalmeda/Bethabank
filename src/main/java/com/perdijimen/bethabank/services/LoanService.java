package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Loan;
import com.perdijimen.bethabank.model.request.LoanRequest;
import com.perdijimen.bethabank.model.response.LoanGetAllResponse;
import com.perdijimen.bethabank.model.response.LoanResponse;

import java.util.List;

public interface LoanService {

    List<LoanGetAllResponse> findAll(Long idUser, Integer limit, Integer page);

    LoanResponse calculateAndcreateLoan(LoanRequest loan);

    Loan createLoan (LoanRequest request, LoanResponse response);

    Loan updateLoan(Loan loan);

    void manageLoan (Loan loan);

}
