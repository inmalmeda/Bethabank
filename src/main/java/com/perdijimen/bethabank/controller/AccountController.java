package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.services.AccountService;
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
 * Rest controller of accounts
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> userListFilter(@RequestParam(name="id", required = false) Long idUser,
                                        @RequestParam(name="name", required = false) String name,
                                     @RequestParam(name="limit", required = false, defaultValue = "5") Integer limit,
                                     @RequestParam(name="page", required = false, defaultValue = "0") Integer page)  {

        if (idUser != null) {
            return this.accountService.findAllByName(name, limit, page) ;
        }
        return this.accountService.findAll(idUser, limit, page);
    }

    @GetMapping("/accounts/{id}")
    public Optional<Account> userFilterById(@PathVariable Long id)  {

        return this.accountService.findById(id);
    }

    /**
     * It saves an account and returns the account created with id
     * @param account New account
     * @return Account created
     */
    @PostMapping("/accounts")
    @ApiOperation("Guarda en base de datos una cuenta nueva")
    public ResponseEntity<Account> createAccount(@ApiParam("Objeto cuenta nueva")
                                           @RequestBody Account account) throws URISyntaxException {

        Account accountDB = accountService.createAccount(account);

        return accountDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.created(new URI("/api/accounts/" + accountDB.getId())).body(accountDB);
    }

    /**
     * It updates an account
     * @param account Account to update
     * @return Response of update account
     */
    @PutMapping("/accounts")
    @ApiOperation("Actualiza en base de datos una cuenta existente")
    public ResponseEntity<Account> updateAccount(@ApiParam("Información de la cuenta") @RequestBody Account account) {

        Account accountDB = accountService.updateAccount(account);

        return accountDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok().body(accountDB);
    }
}
