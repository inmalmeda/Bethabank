package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.model.request.TransactionRequest;
import com.perdijimen.bethabank.repository.TransactionRepository;
import com.perdijimen.bethabank.services.CardService;
import com.perdijimen.bethabank.services.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/transactions")
    @ApiOperation("Encuentra todas las tarjetas con filtro de id de usuario y paginación")
    public ResponseEntity<List<Transaction>> findAll(@RequestParam(name= "id") Long idUser,
                                                     @RequestParam(name= "isIncome") Boolean isIncome,
                                              @RequestParam(name = "page", defaultValue="0") Integer page,
                                              @RequestParam(name = "limit", defaultValue="5") Integer limit) {

        List<Transaction> transactionList = transactionService.findAll(idUser, isIncome, limit, page);

        if (transactionList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok().body(transactionList);
        }
    }

    @GetMapping("/transactions/{id}")
    public Optional<Transaction> userFilterById(@PathVariable Long id)  {

        return this.transactionService.findById(id);
    }

    /**
     * It saves a transaction and returns the transaction created with id
     * @param transactionRequest New transaction
     * @return Transaction created
     */
    @PostMapping("/transactions")
    @ApiOperation("Guarda en base de datos un movimiento nuevo")
    public ResponseEntity<Transaction> createTransaction(@ApiParam("Objeto movimiento nuevo")
                                           @RequestBody TransactionRequest transactionRequest) throws URISyntaxException {

        Transaction transactionDB = transactionService.createTransaction(transactionRequest.getTransaction(), transactionRequest.getIdAccount());

        return transactionDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.created(new URI("/api/transactions/" + transactionDB.getId())).body(transactionDB);
    }

}
