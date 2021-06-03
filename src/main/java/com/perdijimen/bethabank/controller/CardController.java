package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.model.request.CardRequest;
import com.perdijimen.bethabank.model.request.CardUpdateRequest;
import com.perdijimen.bethabank.model.response.CardResponse;
import com.perdijimen.bethabank.services.CardService;
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
 * Rest controller of cards
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "https://ingenia-bank.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class CardController {

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * It returns all cards depending of idUser
     * @return Response with list of user´s cards
     */
    @GetMapping("/cards")
    @ApiOperation("Encuentra todas las tarjetas con filtro de id de usuario y paginación")
    public ResponseEntity<List<CardResponse>> findAll(
            @ApiParam("Id del usuario para buscar las tarjetas")
            @RequestParam(name= "id") Long idUser,
            @ApiParam("Número de tarjetas que se quieren recuperar")
            @RequestParam(name = "limit", defaultValue="3") Integer limit,
            @ApiParam("Número de registro en el que empieza la búsqueda")
            @RequestParam(name = "page", defaultValue="0") Integer page){

        List<CardResponse> cardList = cardService.findAll(idUser, limit, page);

        if(cardList.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return  ResponseEntity.ok().body(cardList);
        }
    }

    /**
     * It returns a card by id
     * @param id Long id of card
     * @return Response with a card from database
     */
    @GetMapping("/cards/{id}")
    @ApiOperation("Encuentra una tarjeta por su id")
    public  ResponseEntity<Optional<Card>>findOne(
            @ApiParam("Clave primaria de la tarjeta")
            @PathVariable Long id) {

        Optional<Card> card = cardService.findById(id);

        if(card == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return  ResponseEntity.ok().body(card);
        }
    }

    /**
     * It saves a card and returns the card created with id
     * @param cardRequest New card
     * @return Card created
     */
    @PostMapping("/cards")
    @ApiOperation("Guarda en base de datos una tarjeta nueva")
    public ResponseEntity<Card> createCard(
            @ApiParam("Objeto tarjeta nueva")
            @RequestBody CardRequest cardRequest) throws URISyntaxException {

        Card cardDB = cardService.createCard(cardRequest);

        return cardDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.created(new URI("/api/cards/" + cardDB.getId())).body(cardDB);
    }

    /**
     * It updates a card
     * @param card Card to update
     * @return Response of update card
     */
    @PutMapping("/cards")
    @ApiOperation("Actualiza en base de datos una tarjeta existente")
    public ResponseEntity<Card> updateCard(
            @ApiParam("Información de la cuenta")
            @RequestBody CardUpdateRequest card) {

        Card cardDB = cardService.updateCard(card);

        return cardDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok().body(cardDB);
    }

    /**
     * It deletes one card by id
     * @param id id of card
     * @return Response of delete
     */
    @DeleteMapping("/cards/{id}")
    @ApiOperation("Borra de base de datos una tarjeta según su id")
    public ResponseEntity deleteCard(@ApiParam("Id de la tarjeta")
                              @PathVariable Long id) {

        if(id!=null){

            if(cardService.deleteCardById(id)){
                return new ResponseEntity(HttpStatus.OK);
            }else{
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
