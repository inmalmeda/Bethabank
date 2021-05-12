package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.repository.TransactionRepository;
import com.perdijimen.bethabank.services.CardService;
import com.perdijimen.bethabank.services.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Rest controller of transactions
 */
@RestController
@RequestMapping("/api")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    /**
     * It saves a transaction and returns the transaction created with id
     * @param transaction New transaction
     * @return Transaction created
     */
    @PostMapping("/transactions")
    @ApiOperation("Guarda en base de datos un movimiento nuevo")
    public ResponseEntity<Transaction> createTransaction(@ApiParam("Objeto movimiento nuevo")
                                           @RequestBody Transaction transaction) throws URISyntaxException {

        Transaction transactionDB = transactionService.createTransaction(transaction);

        return transactionDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.created(new URI("/api/transactions/" + transactionDB.getId())).body(transactionDB);
    }

}
