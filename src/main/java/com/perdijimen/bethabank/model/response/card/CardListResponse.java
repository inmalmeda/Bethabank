package com.perdijimen.bethabank.model.response.card;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CardListResponse {

    List<CardResponse> cardList;

    HttpStatus response;

    public CardListResponse() {
    }

    public CardListResponse(List<CardResponse> cardList) {
        this.cardList = cardList;
    }

    public CardListResponse(List<CardResponse> cardList, HttpStatus response) {
        this.cardList = cardList;
        this.response = response;
    }

    public List<CardResponse> getCardList() {
        return cardList;
    }

    public void setCardList(List<CardResponse> cardList) {
        this.cardList = cardList;
    }

    public HttpStatus getResponse() {
        return response;
    }

    public void setResponse(HttpStatus response) {
        this.response = response;
    }
}
