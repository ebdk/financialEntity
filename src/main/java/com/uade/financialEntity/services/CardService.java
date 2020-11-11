package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardFullResponse;

import java.util.List;

public interface CardService {

	List<CardFullResponse> getAllCards();

	Object get(Long id);

	Object getByCreditNumber(Integer creditNumber);

	Object createCard(List<CardRequest> cardRequests);

	Object closeLastMonthResume(Long id);

	Object getOpenResumeByResumeId(Long idResume);

	Object getOpenResume(Long cardId);

	Object getAllResumes(Long cardId);

	Object payForLastResume(Long cardId, Integer amount);

}
