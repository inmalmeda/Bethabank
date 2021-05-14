package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.services.CardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Rest controller of cards
 */
@RestController
@RequestMapping("/api")
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
    public ResponseEntity<List<Card>> findAll(@RequestParam(name= "id", required = false) Long idUser,
                                              @RequestParam(name = "limit", defaultValue="5") Integer limit,
                                              @RequestParam(name = "page", defaultValue="0") Integer page){

        List<Card> cardList = cardService.findAll(idUser, limit, page);

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
    public  ResponseEntity<Optional<Card>>findOne(@ApiParam("Clave primaria de la tarjeta") @PathVariable Long id) {

        Optional<Card> card = cardService.findById(id);

        if(card == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return  ResponseEntity.ok().body(card);
        }
    }


    /**
     * It saves a card and returns the card created with id
     * @param card New card
     * @return Card created
     */
    @PostMapping("/cards")
    @ApiOperation("Guarda en base de datos una tarjeta nueva")
    public ResponseEntity<Card> createCard(@ApiParam("Objeto tarjeta nueva")
                                                 @RequestBody Card card) throws URISyntaxException {

        Card cardDB = cardService.createCard(card);

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
    public ResponseEntity<Card> updateCard(@ApiParam("Información de la cuenta") @RequestBody Card card) {

        Card cardDB = cardService.updateCard(card);

        return cardDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok().body(cardDB);
    }
}
