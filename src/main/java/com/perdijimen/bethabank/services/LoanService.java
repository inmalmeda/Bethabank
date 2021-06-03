package com.perdijimen.bethabank.services;

import com.perdijimen.bethabank.model.Loan;
import com.perdijimen.bethabank.model.request.LoanRequest;
import com.perdijimen.bethabank.model.response.LoanResponse;

public interface LoanService {

    LoanResponse calculateAndcreateLoan(LoanRequest loan);

    Loan createLoan (LoanRequest request, LoanResponse response);
}
