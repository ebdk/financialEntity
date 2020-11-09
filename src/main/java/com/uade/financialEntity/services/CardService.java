package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardResponse;

import java.util.List;

public interface CardService {

	List<CardResponse> getAllCards();

	Object get(Long id);

	Object getByCreditNumber(Integer creditNumber);

	Object createCard(CardRequest cardRequest);

	Object closeLastMonthResume(Long id);

}
