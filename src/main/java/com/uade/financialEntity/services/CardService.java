package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardFullResponse;
import com.uade.financialEntity.messages.responses.MonthResumeFullResponse;

import java.util.List;

public interface CardService {

	List<CardFullResponse> getAllCards();

	Object get(Long id);

	Object getByCreditNumber(Integer creditNumber);

	Object createCard(List<CardRequest> cardRequests);

	Object closeLastMonthResume(Long id);

	Object closeLastMonthResumes();

	Object getOpenResumeByResumeId(Long idResume);

	Object getOpenResume(Long cardId);

	Object getAllResumes(Long cardId);

	Object payForLastResume(Long cardId, Integer amount);

	Object delete(Long id);

	Object modify(Long id, CardRequest request);

	Object deleteResume(Long id);

	Object existsCreditNumber(Integer creditNumber);

	List<MonthResumeFullResponse> closeLastMonthResumes(Integer month);

}
