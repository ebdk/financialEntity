package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.CardEntityRequest;
import com.uade.financialEntity.messages.responses.CardEntityResponse;
import com.uade.financialEntity.models.CardEntity;
import com.uade.financialEntity.models.ShopPromotion;
import com.uade.financialEntity.repositories.CardEntityDAO;
import com.uade.financialEntity.services.CardEntityService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la entidad de Tarjeta con  id " + id)).getMapMessage();
	}

	@Override
	public Object getPromotions(Long id) {
		Optional<CardEntity> cardEntity = cardEntityRepository.findById(id);
		return cardEntity.isPresent() ?
				cardEntity.get().getShopPromotions().stream().map(ShopPromotion::toDto) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la entidad de Tarjeta con  id " + id)).getMapMessage();
	}

	@Override
	public Object getByName(String name) {
		Optional<CardEntity> cardEntity = cardEntityRepository.findByName(name);
		return cardEntity.isPresent() ?
				new CardEntityResponse(cardEntity.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la entidad de tarjeta con nombre " + name)).getMapMessage();
	}

	@Override
	public Object getBySalary(Long salary) {
		List<CardEntity> cardEntities = cardEntityRepository.findAll()
				.stream()
				.filter(cardEntity -> cardEntity.isAvailableForSalary(salary))
				.collect(Collectors.toList());

		return cardEntities.stream().map(CardEntity::toDto).collect(Collectors.toList());
	}

	@Override
	public Object createCardEntities(List<CardEntityRequest> cardEntityRequests) {
		List<CardEntity> cardEntities = cardEntityRequests
				.stream()
				.map(CardEntityRequest::toEntity)
				.collect(toList());

		cardEntityRepository.saveAll(cardEntities);
		return cardEntities.stream().map(CardEntity::toDto).collect(toList());
	}

	@Override
	public Object delete(Long cardId) {
		cardEntityRepository.deleteById(cardId);
		return new MessageResponse("Removed Succesfuly").getMapMessage();
	}

	@Override
	public Object modify(Long id, CardEntityRequest request) {
		Optional<CardEntity> optionalCardEntity = cardEntityRepository.findById(id);
		if (optionalCardEntity.isPresent()) {
			CardEntity cardEntity = optionalCardEntity.get();
			cardEntity.modify(request);
			cardEntityRepository.save(cardEntity);
			return cardEntity.toDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la Entidad con id " + id)).getMapMessage();
		}
	}

}