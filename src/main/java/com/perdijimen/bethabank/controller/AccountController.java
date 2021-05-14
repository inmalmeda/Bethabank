package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.model.response.AnalyticResponse;
import com.perdijimen.bethabank.model.response.BalanceAnalyticResponse;
import com.perdijimen.bethabank.model.response.CategoryAnalyticResponse;
import com.perdijimen.bethabank.services.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * It returns all accounts depending of idUser
     * @return Response with list of user´s accounts
     */
    @GetMapping("/accounts")
    @ApiOperation("Encuentra todas las cuentas con filtro de id de usuario y paginación")
    public ResponseEntity<List<Account>> findAll(@RequestParam(name="id", required = false) Long idUser,
                                     @RequestParam(name="limit", required = false, defaultValue = "5") Integer limit,
                                     @RequestParam(name="page", required = false, defaultValue = "0") Integer page)  {

        List<Account> accountList = accountService.findAll(idUser, limit, page);

        if(accountList.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return  ResponseEntity.ok().body(accountList);
        }

    }

    /**
     * It returns an account by id
     * @param id Long id of account
     * @return Response with an account from database
     */
    @GetMapping("/accounts/{id}")
    @ApiOperation("Encuentra una cuenta por su id")
    public ResponseEntity<Optional<Account>> findOne(@PathVariable Long id)  {

        Optional<Account> account = accountService.findById(id);

        if(account == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return  ResponseEntity.ok().body(account);
        }
    }

    /**
     * It returns analytics of an account
     * @return Response with analytic of account
     */
    @GetMapping("/accounts/analytics")
    @ApiOperation("Genera un análisis de una cuenta según sus gastos e ingresos")
    public ResponseEntity<List<AnalyticResponse>> analyticAccount(@RequestParam(name="id") Long idAccount,
                                                            @RequestParam(name="typePeriod", defaultValue = "1") Boolean typePeriod)  {

        List<AnalyticResponse> analyticList = accountService.getAnalytics(idAccount, typePeriod);

        return  ResponseEntity.ok().body(analyticList);
    }

    /**
     * It returns analytics group by categories of an account
     * @return Response with analytic of account
     */
    @GetMapping("/accounts/categoryAnalytics")
    @ApiOperation("Genera un análisis de una cuenta según sus gastos agrupados en categorías")
    public ResponseEntity<CategoryAnalyticResponse> analyticCategoryAccount(@RequestParam(name="id") Long idAccount)  {

        CategoryAnalyticResponse analyticCategory = accountService.getAnalyticsCategory(idAccount);

        return  ResponseEntity.ok().body(analyticCategory);
    }

    /**
     * It returns balance´s analytics of an account
     * @return Response with analytic of account
     */
    @GetMapping("/accounts/balanceAnalytics")
    @ApiOperation("Genera un análisis del balance de una cuenta o tarjeta")
    public ResponseEntity<List<BalanceAnalyticResponse>> analyticBalanceAccountOrCard(@RequestParam(name="id") Long id,
                                                                                @RequestParam(name="type") Boolean type,
                                                                                @RequestParam(name="start", required = false) LocalDate startDate,
                                                                                @RequestParam(name="end", required = false) LocalDate endDate)  {


       List<BalanceAnalyticResponse> analyticBalanceList = accountService.getAnalyticsBalance(id, type, startDate, endDate);

       return  ResponseEntity.ok().body(analyticBalanceList);
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
