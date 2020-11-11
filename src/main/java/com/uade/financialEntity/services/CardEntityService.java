package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.CardEntityRequest;
import com.uade.financialEntity.messages.responses.CardEntityResponse;

import java.util.List;

public interface CardEntityService {

	List<CardEntityResponse> getAllCardEntities();

	Object get(Long id);

	Object getPromotions(Long id);

	Object getByName(String name);

	Object createCardEntities(List<CardEntityRequest> cardEntityRequests);

	Object delete(Long cardId);

	Object modify(Long id, CardEntityRequest request);

}
