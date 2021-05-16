package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Account;
import com.perdijimen.bethabank.model.request.AccountRequest;
import com.perdijimen.bethabank.model.request.AccountUpdateRequest;
import com.perdijimen.bethabank.model.response.AnalyticResponse;
import com.perdijimen.bethabank.model.response.BalanceAnalyticResponse;
import com.perdijimen.bethabank.model.response.CategoryAnalyticResponse;
import com.perdijimen.bethabank.services.AccountService;
import com.perdijimen.bethabank.services.AnalyticService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Rest controller of accounts
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;
    private AnalyticService analyticService;

    public AccountController(AccountService accountService, AnalyticService analyticService) {
        this.accountService = accountService;
        this.analyticService = analyticService;
    }

    /**
     * It returns all accounts depending of idUser
     * @return Response with list of user´s accounts
     */
    @GetMapping("/accounts")
    @ApiOperation("Encuentra todas las cuentas con filtro de id de usuario y paginación")
    public ResponseEntity<List<Account>> findAll(
            @ApiParam("Id del usuario de todas las cuentas que se quieren recuperar")
            @RequestParam(name="id", required = false) Long idUser,
            @ApiParam("Cantidad de cuentas que se quieren recuperar")
            @RequestParam(name="limit", required = false, defaultValue = "5") Integer limit,
            @ApiParam("Número de registro en el que empieza la búsqueda")
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

        List<AnalyticResponse> analyticList = analyticService.getAnalytics(idAccount, typePeriod);

        return  ResponseEntity.ok().body(analyticList);
    }

    /**
     * It returns analytics group by categories of an account
     * @return Response with analytic of account
     */
    @GetMapping("/accounts/categoryAnalytics")
    @ApiOperation("Genera un análisis de una cuenta según sus gastos agrupados en categorías")
    public ResponseEntity<CategoryAnalyticResponse> analyticCategoryAccount(@RequestParam(name="id") Long idAccount)  {

        CategoryAnalyticResponse analyticCategory = analyticService.getAnalyticsCategory(idAccount);

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


       List<BalanceAnalyticResponse> analyticBalanceList = analyticService.getAnalyticsBalance(id, type, startDate, endDate);

       return  ResponseEntity.ok().body(analyticBalanceList);
    }


    /**
     * It saves an account and returns the account created with id
     * @param accountRequest New account
     * @return Account created
     */
    @PostMapping("/accounts")
    @ApiOperation("Guarda en base de datos una cuenta nueva")
    public ResponseEntity<Account> createAccount(@ApiParam("Objeto cuenta nueva")
                                           @RequestBody AccountRequest accountRequest) throws URISyntaxException {

        Account accountDB = accountService.createAccount(accountRequest);

        return accountDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.created(new URI("/api/accounts/" + accountDB.getId())).body(accountDB);
    }

    /**
     * It updates an account
     * @param accountRequest Account to update
     * @return Response of update account
     */
    @PutMapping("/accounts")
    @ApiOperation("Actualiza en base de datos una cuenta existente")
    public ResponseEntity<Account> updateAccount(@ApiParam("Información de la cuenta") @RequestBody AccountUpdateRequest accountRequest) {

        Account accountDB = accountService.updateAccount(accountRequest);

        return accountDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok().body(accountDB);
    }
}
