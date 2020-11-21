package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.responses.CardSolicitationResponse;

import java.util.List;

public interface CardSolicitationService {

	List<CardSolicitationResponse> getAllCardSolicitations();

	Object get(Long id);

	Object getByCustomerDni(Long dni);

	Object solicitate(Long customerId, Long cardEntityId);

	Object accept(Long id);

	Object decline(Long id);

	Object delete(Long id);

}
