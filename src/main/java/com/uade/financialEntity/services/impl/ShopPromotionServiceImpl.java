package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.ShopPromotionRequest;
import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.responses.ShopPromotionResponse;
import com.uade.financialEntity.models.CardEntity;
import com.uade.financialEntity.models.Shop;
import com.uade.financialEntity.models.ShopPromotion;
import com.uade.financialEntity.repositories.CardEntityDAO;
import com.uade.financialEntity.repositories.ShopDAO;
import com.uade.financialEntity.repositories.ShopPromotionDAO;
import com.uade.financialEntity.services.ShopPromotionService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ShopPromotionServiceImpl implements ShopPromotionService {

	@Autowired
	private ShopPromotionDAO shopPromotionRepository;

	@Autowired
	private ShopDAO shopRepository;

	@Autowired
	private CardEntityDAO cardEntityRepository;

	@Override
	public List<ShopPromotionResponse> getAllShopPromotions() {
		List<ShopPromotion> shops = shopPromotionRepository.findAll();
		return shops.stream().map(ShopPromotion::toDto).collect(Collectors.toList());
	}


	@Override
	public Object get(Long id) {
		Optional<ShopPromotion> shop = shopPromotionRepository.findById(id);
		return shop.isPresent() ?
				new ShopPromotionResponse(shop.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrado el negocio con id " + id)).getMapMessage();
	}

	@Override
	public List<ShopPromotionResponse> getByShopId(Long id) {
		List<ShopPromotion> shops = shopPromotionRepository.findAll()
				.stream().filter(shopPromotion -> shopPromotion.getShop().getId().equals(id))
				.collect(Collectors.toList());

		return shops.stream().map(ShopPromotion::toDto).collect(Collectors.toList());
	}

	@Override
	public Object createShopPromotions(List<ShopPromotionRequest> shopPromotionRequests) {
		List<ShopPromotion> shopPromotions = new ArrayList<>();
		shopPromotionRequests.forEach(shopPromotionRequest -> {
			ShopPromotion shopPromotion = new ShopPromotion(shopPromotionRequest);

			Long idShop = shopPromotionRequest.getShopId();
			Optional<Shop> optionalShop = shopRepository.findById(idShop);
			optionalShop.ifPresent(shopPromotion::setShop);

			Long idCardEntity = shopPromotionRequest.getCardEntityId();
			Optional<CardEntity> optionalCardEntity = cardEntityRepository.findById(idCardEntity);
			optionalCardEntity.ifPresent(shopPromotion::setCardEntity);

			shopPromotions.add(shopPromotion);
		});

		shopPromotionRepository.saveAll(shopPromotions);
		return shopPromotions.stream().map(ShopPromotion::toDto).collect(toList());
	}

	@Override
	public Object delete(Long id) {
		shopPromotionRepository.deleteById(id);
		return new MessageResponse("Removed Succesfuly");
	}

	@Override
	public Object modify(Long id, ShopPromotionRequest request) {
		Optional<ShopPromotion> optionalShopPromotion = shopPromotionRepository.findById(id);
		if (optionalShopPromotion.isPresent()) {
			ShopPromotion shopPromotion = optionalShopPromotion.get();
			shopPromotion.modify(request);
			shopPromotionRepository.save(shopPromotion);
			return shopPromotion.toDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada el negocio con id " + id)).getMapMessage();
		}
	}

}