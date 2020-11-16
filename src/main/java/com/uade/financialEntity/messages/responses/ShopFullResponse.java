package com.uade.financialEntity.messages.responses;

import com.uade.financialEntity.messages.Response;
import com.uade.financialEntity.models.Shop;
import com.uade.financialEntity.models.ShopPayment;
import com.uade.financialEntity.models.ShopPromotion;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ShopFullResponse implements Response {

	//ATTRIBUTES
	private Long id;
	private UserResponse user;
	private List<ShopPaymentResponse> purchases;
	//private Map<Integer, List<ShopPaymentResponse>> purchasesMap;
	private List<ShopPromotionResponse> promotions;
	private String name;
	private String imgUrl;
	private Integer cuit;

	//BUILDERS
	public ShopFullResponse(Shop shop) {
		if (shop != null) {
			this.id = shop.getId() != null ? shop.getId() : null;
			this.user = shop.getUser() != null ? shop.getUser().toDto() : null;
			this.purchases = shop.getPurchases() != null
					? shop.getPurchases()
					.stream()
					.map(ShopPayment::toDto)
					.collect(Collectors.toList()) : null;
			/*this.purchasesMap = shop.getPurchases() != null
					? shop.getPurchases()
					.stream()
					.map(ShopPayment::toDto)
					.collect(groupingBy(ShopPayment::getMonth)) : null;*/
			this.promotions = shop.getShopPromotions() != null
					? shop.getShopPromotions().stream().map(ShopPromotion::toDto).collect(Collectors.toList()) : null;
			this.name = shop.getName() != null ? shop.getName() : null;
			this.imgUrl = shop.getImgUrl() != null ? shop.getImgUrl() : null;
			this.cuit = shop.getCuit() != null ? shop.getCuit() : null;
		}
	}

	public ShopFullResponse() {
	}

}
