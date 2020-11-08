package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.CardRequest;

import java.util.List;

public interface CardService {
	Object getRandomCard(String cardType, String cardDifficulty);

	Object createCard(List<CardRequest> cardRequestList);
}
