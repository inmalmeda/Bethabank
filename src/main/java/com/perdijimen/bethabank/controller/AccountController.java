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
//@CrossOrigin(origins = "https://ingenia-bank.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
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
            @RequestParam(name="id") Long idUser,
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
    public ResponseEntity<Optional<Account>> findOne(
            @ApiParam("Id de la cuenta que se quiere recuperar")
            @PathVariable Long id)  {

        Optional<Account> account = accountService.findById(id);

        if(!account.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok().body(account);
        }
    }

    /**
     * It returns analytics of an account
     * @return Response with analytic of account
     */
    @GetMapping("/accounts/analytics")
    @ApiOperation("Genera un análisis de una cuenta según sus gastos e ingresos")
    public ResponseEntity<List<AnalyticResponse>> analyticAccount(
            @ApiParam("Id de la cuenta para hacer el análisis")
            @RequestParam(name="id") Long idAccount,
            @ApiParam("Tipo de periodo-> False: Año, True: Mes")
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
    public ResponseEntity<CategoryAnalyticResponse> analyticCategoryAccount(
            @ApiParam("Id de la cuenta para hacer el análisis del mes por categoría")
            @RequestParam(name="id") Long idAccount)  {

        CategoryAnalyticResponse analyticCategory = analyticService.getAnalyticsCategory(idAccount);

        return  ResponseEntity.ok().body(analyticCategory);
    }

    /**
     * It returns balance´s analytics of an account
     * @return Response with analytic of account
     */
    @GetMapping("/accounts/balanceAnalytics")
    @ApiOperation("Genera un análisis del balance de una cuenta o tarjeta")
    public ResponseEntity<List<BalanceAnalyticResponse>> analyticBalanceAccountOrCard(
            @ApiParam("Id de la cuenta para hacer el análisis del balance, si es nulo se hará el balance de todas las cuentas")
            @RequestParam(name="id", required = false) Long id,
            @ApiParam("Id del usuario para hacer el análisis de todas las cuentas")
            @RequestParam(name="iduser", required = false) Long idUser,
            @ApiParam("Tipo de balance-> True: Cuenta, False: Tarjeta")
            @RequestParam(name="type", required = false) Boolean type,
            @ApiParam("Fecha de inicio del análisis")
            @RequestParam(name="start", required = false) String startDate,
            @ApiParam("Fecha de inicio del final")
            @RequestParam(name="end", required = false) String endDate)  {

       List<BalanceAnalyticResponse> analyticBalanceList = analyticService.getAnalyticsBalance(id, idUser, type, startDate, endDate);

       return  ResponseEntity.ok().body(analyticBalanceList);
    }


    /**
     * It saves an account and returns the account created with id
     * @param accountRequest New account
     * @return Account created
     */
    @PostMapping("/accounts")
    @ApiOperation("Guarda en base de datos una cuenta nueva")
    public ResponseEntity<Account> createAccount(
            @ApiParam("Objeto de solicitud de cuenta nueva")
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
    public ResponseEntity<Account> updateAccount(
            @ApiParam("Información para actualizar la cuenta")
            @RequestBody AccountUpdateRequest accountRequest) {

        Account accountDB = accountService.updateAccount(accountRequest);

        return accountDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok().body(accountDB);
    }

    /**
     * It deletes one account by id
     * @param id id of account
     * @return Response of delete
     */
    @DeleteMapping("/accounts/{id}")
    @ApiOperation("Borra de base de datos una cuenta según su id")
    public ResponseEntity deleteAccount(@ApiParam("Id de la cuenta")
                                     @PathVariable Long id) {

        if(id!=null){

            if(accountService.deleteAccountById(id)){
                return new ResponseEntity(HttpStatus.OK);
            }else{
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
