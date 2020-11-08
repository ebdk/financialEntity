package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CardRequest;
import com.uade.financialEntity.messages.responses.CardResponse;
import com.uade.financialEntity.models.Card;
import com.uade.financialEntity.repositories.CardDAO;
import com.uade.financialEntity.services.CardService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardDAO cardRepository;

	@Override
	public List<CardResponse> getAllCards() {
		List<Card> personas = cardRepository.findAll();
		return personas.stream().map(Card::toDto).collect(Collectors.toList());
	}

	@Override
	public Object get(Long id) {
		Optional<Card> persona = cardRepository.findById(id);
		return persona.isPresent() ?
				new CardResponse(persona.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la persona con id " + id)).getMapMessage();
	}

	@Override
	public Object getByCreditNumber(Integer creditnumber) {
		Optional<Card> persona = cardRepository.findByCreditNumber(creditnumber);
		return persona.isPresent() ?
				new CardResponse(persona.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la carta con numero " + creditnumber)).getMapMessage();
	}

	@Override
	public Object createCard(CardRequest cardRequest) {
		Card newcard = new Card(cardRequest);
		cardRepository.save(newcard);
		return newcard.toDto();
	}

}