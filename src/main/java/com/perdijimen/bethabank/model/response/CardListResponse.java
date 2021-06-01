package com.perdijimen.bethabank.model.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CardListResponse {
    private List<CardResponse> cardList;

    private HttpStatus responseStatus;

    public CardListResponse() {
    }

    public CardListResponse(List<CardResponse> cardList) {
        this.cardList = cardList;
    }

    public List<CardResponse> getCardList() {
        return cardList;
    }

    public void setCardList(List<CardResponse> cardList) {
        this.cardList = cardList;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(HttpStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
