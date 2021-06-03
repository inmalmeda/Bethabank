package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.request.AccountRequest;
import com.perdijimen.bethabank.model.request.LoanRequest;
import com.perdijimen.bethabank.model.response.LoanResponse;
import com.perdijimen.bethabank.services.LoanService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Rest controller of loans
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "https://ingenia-bank.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    //GETALLDELUSER


    /**
     * It calculates or/and saves a loan with its fees and amounts
     * @param loanRequest New loan
     * @return Loan calculated or/and created
     */
    @PostMapping("/loans")
    @ApiOperation("Calcula un préstamo o/y lo guarda en base de datos")
    public ResponseEntity<LoanResponse> createLoan(
            @ApiParam("Objeto de solicitud de un préstamo nuevo")
            @RequestBody LoanRequest loanRequest) throws URISyntaxException {

        LoanResponse response = loanService.calculateAndcreateLoan(loanRequest);

        return response == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok().body(response);
    }
}
