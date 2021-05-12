package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.Card;
import com.perdijimen.bethabank.services.CardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

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
