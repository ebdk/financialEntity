package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.ShopPromotionRequest;
import com.uade.financialEntity.messages.responses.ShopPromotionResponse;
import com.uade.financialEntity.models.ShopPromotion;
import com.uade.financialEntity.repositories.ShopPromotionDAO;
import com.uade.financialEntity.services.ShopPromotionService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopPromotionServiceImpl implements ShopPromotionService {

	@Autowired
	private ShopPromotionDAO shopPromotionRepository;

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
	public Object createShopPromotion(ShopPromotionRequest shopRequest) {
		ShopPromotion newShopPromotion = new ShopPromotion(shopRequest);
		shopPromotionRepository.save(newShopPromotion);
		return newShopPromotion.toDto();
	}

}