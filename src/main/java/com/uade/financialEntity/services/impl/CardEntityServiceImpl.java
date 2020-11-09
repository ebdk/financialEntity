package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CardEntityRequest;
import com.uade.financialEntity.messages.responses.CardEntityResponse;
import com.uade.financialEntity.models.CardEntity;
import com.uade.financialEntity.repositories.CardEntityDAO;
import com.uade.financialEntity.services.CardEntityService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardEntityServiceImpl implements CardEntityService {

	@Autowired
	private CardEntityDAO cardEntityRepository;

	@Override
	public List<CardEntityResponse> getAllCardEntities() {
		List<CardEntity> cardEntities = cardEntityRepository.findAll();
		return cardEntities.stream().map(CardEntity::toDto).collect(Collectors.toList());
	}

	@Override
	public Object get(Long id) {
		Optional<CardEntity> cardEntity = cardEntityRepository.findById(id);
		return cardEntity.isPresent() ?
				new CardEntityResponse(cardEntity.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la entidad de carta con  id " + id)).getMapMessage();
	}

	@Override
	public Object getByName(String name) {
		Optional<CardEntity> cardEntity = cardEntityRepository.findByName(name);
		return cardEntity.isPresent() ?
				new CardEntityResponse(cardEntity.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la entidad de carta con nombre " + name)).getMapMessage();
	}

	@Override
	public Object createCardEntity(CardEntityRequest cardRequest) {
		CardEntity newcard = new CardEntity(cardRequest);
		cardEntityRepository.save(newcard);
		return newcard.toDto();
	}

}