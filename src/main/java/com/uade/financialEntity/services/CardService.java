package com.uade.financialEntity.services;

public interface CardService {
    Object getRandomCard(String cardType, String cardDifficulty);

    Object createCard(java.util.List<com.uade.financialEntity.messages.requests.CardRequest> cardRequestList);
}
