package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardFullResponse;

import java.util.List;

public interface CardService {

	List<CardFullResponse> getAllCards();

	Object get(Long id);

	Object getByCreditNumber(Long creditNumber);

	Object getOpenResumeByResumeId(Long idResume);

	Object getOpenResume(Long cardId);

	Object getAllResumes(Long cardId);

	Object payForLastResume(Long cardId, Integer amount);

	Object delete(Long id);

	Object modify(Long id, CardRequest request);

	Object deleteResume(Long id);

	Object existsCreditNumber(Long creditNumber);

	void closeLastMonthResumes(Integer month);

}
