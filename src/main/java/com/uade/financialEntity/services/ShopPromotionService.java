package com.uade.financialEntity.services;

import com.uade.financialEntity.messages.requests.ShopPromotionRequest;
import com.uade.financialEntity.messages.responses.ShopPromotionResponse;

import java.util.List;

public interface ShopPromotionService {

	List<ShopPromotionResponse> getAllShopPromotions();

	Object get(Long id);

	List<ShopPromotionResponse> getByShopId(Long id);

	Object createShopPromotions(List<ShopPromotionRequest> shopPromotionRequests);

}
