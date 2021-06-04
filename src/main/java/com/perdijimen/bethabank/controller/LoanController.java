package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.request.LoanRequest;
import com.perdijimen.bethabank.model.response.CardResponse;
import com.perdijimen.bethabank.model.response.LoanGetAllResponse;
import com.perdijimen.bethabank.model.response.LoanResponse;
import com.perdijimen.bethabank.services.LoanService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Rest controller of loans
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://ingenia-bank.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
//@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/loans")
    @ApiOperation("Encuentra todos los prestamos con filtro de id de usuario y paginación")
    public ResponseEntity<List<LoanGetAllResponse>> findAll(
            @ApiParam("Id del usuario para buscar los préstamos")
            @RequestParam(name= "id") Long idUser,
            @ApiParam("Número de préstamos que se quieren recuperar")
            @RequestParam(name = "limit", defaultValue="5") Integer limit,
            @ApiParam("Número de registro en el que empieza la búsqueda")
            @RequestParam(name = "page", defaultValue="0") Integer page){

        List<LoanGetAllResponse> loanList = loanService.findAll(idUser, limit, page);

        if(loanList.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return  ResponseEntity.ok().body(loanList);
        }
    }

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
