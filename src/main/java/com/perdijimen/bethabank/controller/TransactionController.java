package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Transaction;
import com.perdijimen.bethabank.model.request.TransactionRequest;
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
//@CrossOrigin(origins = "https://ingenia-bank.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    @ApiOperation("Encuentra todos los movimientos con filtro de id de usuario y paginación")
    public ResponseEntity<List<Transaction>> findAll(
            @ApiParam("Id del usuario para buscar sus movimientos")
            @RequestParam(name= "id") Long idUser,
            @ApiParam("Tipo de movimiento-> False: Gastos y True:Ingresos")
            @RequestParam(name= "isIncome", required = false) Boolean isIncome,
            @ApiParam("Número de movimientos que se quieren recuperar")
            @RequestParam(name = "limit", defaultValue="7") Integer limit,
            @ApiParam("Número de registro en el que empieza la búsqueda")
            @RequestParam(name = "page", defaultValue="0") Integer page ) {

        List<Transaction> transactionList = transactionService.findAll(idUser, isIncome, limit, page);

        if (transactionList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok().body(transactionList);
        }
    }

    @GetMapping("/transactions/{id}")
    public Optional<Transaction> transactionFilterById(
            @ApiParam("Clave primaria del movimiento")
            @PathVariable Long id)  {

        return this.transactionService.findById(id);
    }

    /**
     * It saves a transaction and returns the transaction created with id
     * @param transactionRequest New transaction
     * @return Transaction created
     */
    @PostMapping("/transactions")
    @ApiOperation("Guarda en base de datos un movimiento nuevo")
    public ResponseEntity<Transaction> createTransaction(
            @ApiParam("Objeto movimiento nuevo")
            @RequestBody TransactionRequest transactionRequest) throws URISyntaxException {

        Transaction transactionDB = transactionService.createTransaction(transactionRequest);

        return transactionDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.created(new URI("/api/transactions/" + transactionDB.getId())).body(transactionDB);
    }



}
